package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.User;

public class ClothingItemActivity extends AppCompatActivity
{
    User user;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_item);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
//        type = intent.getStringExtra("type");
    }

    public void goClothingTypeActivity(View v)
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}