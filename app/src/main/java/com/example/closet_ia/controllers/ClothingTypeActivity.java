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
import android.widget.TextView;
import android.widget.Toast;

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
    CTRecyclerAdapter adapter;

    TextView clothingType;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_type);

        clothingItems = new ArrayList<>();
        typeItems = new ArrayList<>();
        CTRecyclerView = findViewById(R.id.CTRecyclerView);
        searchView = findViewById(R.id.CTSearchView);
        searchView.clearFocus();

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        user = (User) intent.getSerializableExtra("user");

        clothingType = findViewById(R.id.clothingTypeTextView);
        clothingType.setText(type);

        setItems();
        setAdapter();
        setSearchView();
    }

    public void setItems()
    {
        clothingItems = user.getClothingItems();

        for (ClothingItem item : clothingItems)
        {
            if (item.getType().equals(type) && !item.isInWash())
            {
                typeItems.add(item);
            }
        }

//        ClothingItem a = new ClothingItem(UUID.randomUUID().toString(), "shirt", "bobbus", 123456, "03/04/1999");
//        ClothingItem b = new ClothingItem(UUID.randomUUID().toString(), "pants", "joshua", Color.YELLOW, "11/10/2004");
//        clothingItems.add(a);
//        clothingItems.add(b);
    }

    public void setSearchView()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
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

       for (ClothingItem item : typeItems)
       {
           if (item.getName().toLowerCase().contains(s.toLowerCase()))
           {
               filteredList.add(item);
           }
       }

       if (filteredList.isEmpty())
       {
           Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
       }
       else
       {
           adapter.setFilteredList(filteredList);
       }
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
                intent.putExtra("origin", "ClothingTypeActivity");
                startActivity(intent);
            }
        };
    }

    public void setAdapter()
    {
        setOnClickListener();
        adapter = new CTRecyclerAdapter(typeItems, listener);
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