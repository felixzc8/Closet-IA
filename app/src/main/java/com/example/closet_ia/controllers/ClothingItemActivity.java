package com.example.closet_ia.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClothingItemActivity extends AppCompatActivity
{
    FirebaseFirestore firestore;

    User user;
    ArrayList<ClothingItem> clothingItems;
    ClothingItem item;
    String type;
    String origin;
    int position;

    TextView itemNameTextView, typeTextView, lastUsedTextView, datePurchasedTextView, timesWashedTextView;
    ImageView itemColorImageView, washImageView, typeImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_item);

        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        item = (ClothingItem) intent.getSerializableExtra("item");
        origin = intent.getStringExtra("origin");
        clothingItems = user.getClothingItems();

        type = item.getType();

        itemNameTextView = findViewById(R.id.itemNameTextView);
        typeTextView = findViewById(R.id.itemTypeTextView);
        lastUsedTextView = findViewById(R.id.itemLastUsedTextView);
        datePurchasedTextView = findViewById(R.id.itemDatePurchasedTextView);
        timesWashedTextView = findViewById(R.id.itemTimesWashedTextView);
        itemColorImageView = findViewById(R.id.itemColorImageView);
        washImageView = findViewById(R.id.washImageView);
        typeImageView = findViewById(R.id.typeImageView);


        getPosition();
        setDisplay();
    }

    public void getPosition()
    {
        for (int i = 0; i < clothingItems.size(); i++)
        {
            if (clothingItems.get(i).getID().equals(item.getID()))
            {
                position = i;
            }
        }
    }

    public void setDisplay()
    {
        itemNameTextView.setText(item.getName());
        typeTextView.setText(type);
        String lastUsed = "Last used: " + item.getLastUsed();
        lastUsedTextView.setText(lastUsed);
        String datePurchased = "Date purchased: " + item.getDatePurchased();
        datePurchasedTextView.setText(datePurchased);
        String timesWashed = "Times washed: " + item.getTimesWashed();
        timesWashedTextView.setText(timesWashed);
        ImageViewCompat.setImageTintList(itemColorImageView, ColorStateList.valueOf(item.getColor()));

        if (item.isInWash())
        {
            washImageView.setImageResource(R.drawable.laundry);
        }
        else
        {
            washImageView.setImageResource(R.drawable.washing_machine);
        }

        switch (type)
        {
            case "shirts":
                typeImageView.setImageResource(R.drawable.shirt);
                break;
            case "pants":
                typeImageView.setImageResource(R.drawable.pants);
                break;
            case "shoes":
                typeImageView.setImageResource(R.drawable.shoes);
                break;
            case "outerwear":
                typeImageView.setImageResource(R.drawable.jacket);
                break;
            case "underwear & socks":
                typeImageView.setImageResource(R.drawable.underwear);
                break;
            case "dresses":
                typeImageView.setImageResource(R.drawable.dress);
                break;
            case "accessories":
                typeImageView.setImageResource(R.drawable.accessories);
                break;
        }
    }

    public void takeOutWash()
    {
        item.setInWash(false);
        clothingItems.set(position, item);
        user.setClothingItems(clothingItems);

        updateFirestoreClothingItems();

        Intent intent = new Intent(this, ClothingTypeActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void putInWash()
    {
        item.setInWash(true);
        item.setTimesWashed(item.getTimesWashed() + 1);
        clothingItems.set(position, item);
        user.setClothingItems(clothingItems);
        updateFirestoreClothingItems();

        Intent intent = new Intent(this, WashingActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void updateFirestoreClothingItems()
    {
        firestore.collection("users")
                .document(user.getID())
                .update("clothingItems", clothingItems)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.d("CI", "Document successfully updated");
                        }
                        else
                        {
                            Log.w("CI", "Error updating document");
                            System.out.println(task.getException());
                        }
                    }
                });
    }

    public void washIconClicked(View v)
    {
        if (item.isInWash())
        {
            takeOutWash();
        }
        else
        {
            putInWash();
        }
    }

    public void useItem(View v)
    {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = df.format(d);
        System.out.println(date);

        String msg = "Last used: " + date;
        lastUsedTextView.setText(msg);
        item.setLastUsed(date);
        clothingItems.set(position, item);
        user.setClothingItems(clothingItems);
        updateFirestoreClothingItems();
    }

    public void deleteItem(View v)
    {
        clothingItems.remove(position);
        user.setClothingItems(clothingItems);
        updateFirestoreClothingItems();

        Toast.makeText(this, "item deleted", Toast.LENGTH_SHORT).show();

        goClothingTypeActivity();
    }

    public void back(View v)
    {

        switch (origin)
        {
            case "ClothingTypeActivity":
                goClothingTypeActivity();
                break;
            case "WashingActivity":
                goWashingActivity();
                break;
            case "ColorActivity":
                goColorActivity(v);
        }
    }

    public void goColorActivity(View v)
    {
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("item", item);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void goWashingActivity()
    {
        Intent intent = new Intent(this, WashingActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void goClothingTypeActivity()
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}