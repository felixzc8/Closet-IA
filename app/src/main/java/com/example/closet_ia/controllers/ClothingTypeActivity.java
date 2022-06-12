package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.R;
import com.example.closet_ia.objects.User;

import java.util.ArrayList;
import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

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
    ImageView colorWheelImageView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_type);

        clothingItems = new ArrayList<>();
        typeItems = new ArrayList<>();
        CTRecyclerView = findViewById(R.id.CTRecyclerView);
        clothingType = findViewById(R.id.clothingTypeTextView);
        clothingType.setText(type);
        colorWheelImageView = findViewById(R.id.colorWheelImageView);
        searchView = findViewById(R.id.CTSearchView);
        searchView.clearFocus();

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        user = (User) intent.getSerializableExtra("user");

        colorWheelImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openColorPicker();
            }
        });
        setItems();
        setAdapter();
        setSearchView();
    }

    public void setItems()
    {
        clothingItems = user.getClothingItems();

        if (type.equals("all"))
        {
            typeItems = clothingItems;

        }
        else
        {
            for (ClothingItem item : clothingItems)
            {
                if (item.getType().equals(type) && !item.isInWash())
                {
                    typeItems.add(item);
                }
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

        adapter.setFilteredList(filteredList);
    }

    public void openColorPicker()
    {
//        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this,
//                chosenColor, new AmbilWarnaDialog.OnAmbilWarnaListener()
//        {
//            @Override
//            public void onCancel(AmbilWarnaDialog dialog)
//            {
//            }
//
//            @Override
//            public void onOk(AmbilWarnaDialog dialog, int color)
//            {
//                System.out.println(color);
//                chosenColor = color;
//                choseColor = true;
//                colorButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_bg));
//                colorButton.setBackgroundColor(color);
//                String hex = Integer.toHexString(color);
//                System.out.println(hex);
//            }
//        });
//        colorPicker.show();
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