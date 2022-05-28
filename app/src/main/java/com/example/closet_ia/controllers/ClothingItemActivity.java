package com.example.closet_ia.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ClothingItemActivity extends AppCompatActivity
{
    FirebaseFirestore firestore;

    User user;
    ArrayList<ClothingItem> clothingItems;
    ClothingItem item;
    String type;
    int position;

    TextView itemNameTextView, typeTextView, lastUsedTextView, datePurchasedTextView, timesWashedTextView;
    ImageView itemColorImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_item);

        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        item = (ClothingItem) intent.getSerializableExtra("item");
        clothingItems = user.getClothingItems();
        position = (int) intent.getIntExtra("position", 0);

        type = item.getType();

        itemNameTextView = findViewById(R.id.itemNameTextView);
        typeTextView = findViewById(R.id.itemTypeTextView);
        lastUsedTextView = findViewById(R.id.itemLastUsedTextView);
        datePurchasedTextView = findViewById(R.id.itemDatePurchasedTextView);
        timesWashedTextView = findViewById(R.id.itemTimesWashedTextView);
        itemColorImageView = findViewById(R.id.itemColorImageView);

        setDisplay();
    }

    public void setDisplay()
    {
        itemNameTextView.setText(item.getName());
        typeTextView.setText(type);
        lastUsedTextView.setText(item.getLastUsed());
        datePurchasedTextView.setText(item.getDatePurchased());
        timesWashedTextView.setText(String.valueOf(item.getTimesWashed()));
        ImageViewCompat.setImageTintList(itemColorImageView, ColorStateList.valueOf(item.getColor()));
    }

    public void washItem(View v)
    {
        item.setInWash(true);
        clothingItems.set(position, item);
        user.setClothingItems(clothingItems);

        firestore.collection("users")
                .document(user.getID())
                .update("inWash", true)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                        }
                        else
                        {

                        }
                    }
                });

        Intent intent = new Intent(this, WashingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
    public void goClothingTypeActivity(View v)
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}