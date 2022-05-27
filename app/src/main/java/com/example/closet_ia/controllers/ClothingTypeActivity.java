package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    private ArrayList<ClothingItem> clothingItems;
    private RecyclerView recyclerView;

    TextView clothingType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_type);

        clothingItems = new ArrayList<>();
        recyclerView = findViewById(R.id.CTRecyclerView);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        user = (User) intent.getSerializableExtra("user");

        clothingType = findViewById(R.id.clothingTypeTextView);
        clothingType.setText(type);

        setClothingItems();
        setAdapter();
    }

    public void setClothingItems()
    {
        ClothingItem a = new ClothingItem(UUID.randomUUID().toString(), "shirt", "bobbus", 123456, "03/04/1999");
        clothingItems.add(a);
    }

    public void setAdapter()
    {
        CTRecyclerAdapter adapter = new CTRecyclerAdapter(clothingItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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