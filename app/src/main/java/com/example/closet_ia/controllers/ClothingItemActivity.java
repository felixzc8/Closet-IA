package com.example.closet_ia.controllers;

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

public class ClothingItemActivity extends AppCompatActivity
{
    User user;
    ClothingItem item;
    String type;

    TextView itemNameTextView, typeTextView, lastUsedTextView, datePurchasedTextView, timesWashedTextView;
    ImageView itemColorImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_item);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        item = (ClothingItem) intent.getSerializableExtra("item");

//        type = intent.getStringExtra("type");

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
    public void goClothingTypeActivity(View v)
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}