package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.objects.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class WashingActivity extends AppCompatActivity
{
    User user;

    ArrayList<ClothingItem> clothingItems;
    RecyclerView washRecyclerView;
    WashRecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        clothingItems = new ArrayList<>();
        washRecyclerView = findViewById(R.id.washRecyclerView);

        setClothingItems();
        setAdapter();
    }

    public void setClothingItems()
    {
        ClothingItem a = new ClothingItem(UUID.randomUUID().toString(), "shirt", "bobbus", 123456, "03/04/1999");
        ClothingItem b = new ClothingItem(UUID.randomUUID().toString(), "pants", "joshua", Color.YELLOW, "11/10/2004");
        clothingItems.add(a);
        clothingItems.add(b);
    }

    private void setOnClickListener()
    {
        listener = new WashRecyclerAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent intent = new Intent(getApplicationContext(), ClothingItemActivity.class);
                intent.putExtra("clothing item", clothingItems.get(position));
                intent.putExtra("user", user);
                startActivity(intent);
            }
        };
    }

    public void setAdapter()
    {
        setOnClickListener();
        WashRecyclerAdapter adapter = new WashRecyclerAdapter(clothingItems, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        washRecyclerView.setLayoutManager(layoutManager);
        washRecyclerView.setItemAnimator(new DefaultItemAnimator());
        washRecyclerView.setAdapter(adapter);
    }

    public void goClosetActivity(View v)
    {
        startActivity(new Intent(this, ClosetActivity.class));
    }
}