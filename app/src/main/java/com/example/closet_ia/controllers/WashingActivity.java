package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

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
    ArrayList<ClothingItem> washingItems;
    RecyclerView washRecyclerView;
    WashRecyclerAdapter adapter;
    WashRecyclerAdapter.RecyclerViewClickListener listener;

    SearchView washSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        clothingItems = user.getClothingItems();
        washingItems = new ArrayList<>();
        washRecyclerView = findViewById(R.id.washRecyclerView);
        washSearchView = findViewById(R.id.washSearchView);
        washSearchView.clearFocus();

        setClothingItems();
        setAdapter();
        setSearchView();
    }

    public void setClothingItems()
    {
        for (ClothingItem item : clothingItems)
        {
            if (item.isInWash())
            {
                washingItems.add(item);
            }
        }

//        ClothingItem a = new ClothingItem(UUID.randomUUID().toString(), "shirt", "bobbus", 123456, "03/04/1999");
//        ClothingItem b = new ClothingItem(UUID.randomUUID().toString(), "pants", "joshua", Color.YELLOW, "11/10/2004");
//        clothingItems.add(a);
//        clothingItems.add(b);
    }

    private void setOnClickListener()
    {
        listener = new WashRecyclerAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent intent = new Intent(getApplicationContext(), ClothingItemActivity.class);
                intent.putExtra("clothing item", washingItems.get(position));
                intent.putExtra("user", user);
                intent.putExtra("item", washingItems.get(position));
                intent.putExtra("origin", "WashingActivity");
                startActivity(intent);
            }
        };
    }

    public void setSearchView()
    {
        washSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                filterList(s);
                return true;
            }
        });
    }

    private void filterList(String s)
    {
        ArrayList<ClothingItem> filteredList = new ArrayList<>();
        for (ClothingItem item : washingItems)
        {
            if (item.getName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.add(item);
            }
        }

        adapter.setFilteredList(filteredList);
    }

    public void setAdapter()
    {
        setOnClickListener();
        adapter = new WashRecyclerAdapter(washingItems, listener);
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