package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.R;
import com.example.closet_ia.objects.User;

import java.util.ArrayList;
import java.util.UUID;

public class ClothingTypeActivity extends AppCompatActivity
{
    User user;
    String type;
    ArrayList<ClothingItem> clothingItems;
    ArrayList<ClothingItem> typeItems;
    RecyclerView CTRecyclerView;
    CTRecyclerAdapter.RecyclerViewClickListener listener;

    TextView clothingType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_type);

        clothingItems = new ArrayList<>();
        typeItems = new ArrayList<>();
        CTRecyclerView = findViewById(R.id.CTRecyclerView);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        user = (User) intent.getSerializableExtra("user");

        clothingType = findViewById(R.id.clothingTypeTextView);
        clothingType.setText(type);

        setItems();
        setAdapter();
    }

    public void setItems()
    {
        clothingItems = user.getClothingItems();

        for (ClothingItem item : clothingItems)
        {
            if (item.getType().equals(type))
            {
                typeItems.add(item);
            }
        }

//        ClothingItem a = new ClothingItem(UUID.randomUUID().toString(), "shirt", "bobbus", 123456, "03/04/1999");
//        ClothingItem b = new ClothingItem(UUID.randomUUID().toString(), "pants", "joshua", Color.YELLOW, "11/10/2004");
//        clothingItems.add(a);
//        clothingItems.add(b);
    }

    public void setOnClickListener()
    {
        listener = new CTRecyclerAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent intent = new Intent(getApplicationContext(), ClothingItemActivity.class);
                intent.putExtra("item", typeItems.get(position));
                intent.putExtra("user", user);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        };
    }

    public void setAdapter()
    {
        setOnClickListener();
        CTRecyclerAdapter adapter = new CTRecyclerAdapter(typeItems, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        CTRecyclerView.setLayoutManager(layoutManager);
        CTRecyclerView.setItemAnimator(new DefaultItemAnimator());
        CTRecyclerView.setAdapter(adapter);
    }

    public void goClosetActivity(View v)
    {
        startActivity(new Intent(this, ClosetActivity.class));
    }

    public void goAddItemActivity(View v)
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}