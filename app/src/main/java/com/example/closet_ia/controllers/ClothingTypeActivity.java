package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

/**
 * This activity contains a recyclerview of a specific type of clothes
 * Clicking on the RecyclerView will bring the user to a page that contains more information on the
 * item
 * The user can search the items by the item name, or sort the clothes by similarity to a color
 */
public class ClothingTypeActivity extends AppCompatActivity
{
    User user;
    String sortType;
    String origin;
    ArrayList<ClothingItem> clothingItems;
    ArrayList<ClothingItem> typeItems;
    ArrayList<ClothingItem> filteredList;
    RecyclerView CTRecyclerView;
    CTRecyclerAdapter.RecyclerViewClickListener listener;
    CTRecyclerAdapter adapter;

    int chosenColor = 0;
    boolean choseColor = false;

    TextView clothingType;
    ImageView colorWheelImageView;
    SearchView searchView;
    Button goAddItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_type);

        Intent intent = getIntent();
        sortType = intent.getStringExtra("sortType");
        user = (User) intent.getSerializableExtra("user");
        origin = intent.getStringExtra("origin");

        clothingItems = new ArrayList<>();
        typeItems = new ArrayList<>();
        filteredList = new ArrayList<>();
        CTRecyclerView = findViewById(R.id.CTRecyclerView);
        clothingType = findViewById(R.id.clothingTypeTextView);
        clothingType.setText(sortType);
        colorWheelImageView = findViewById(R.id.colorWheelImageView);
        searchView = findViewById(R.id.CTSearchView);
        searchView.clearFocus();
        goAddItemButton = findViewById(R.id.goAddItemButton);

        setItems();
        setAdapter();
        setSearchView();
    }

    /**
     * Selects the clothing items that are of the current type to be displayed
     */
    public void setItems()
    {
        clothingItems = user.getClothingItems();

        if (sortType.equals("all"))
        {
            for (ClothingItem item : clothingItems)
            {
                if (!item.isInWash())
                {
                    typeItems.add(item);
                }
            }
            goAddItemButton.setVisibility(View.GONE);
        }
        else if (sortType.equals("Wash"))
        {
            goAddItemButton.setVisibility(View.GONE);
            clothingType.setTextColor(getResources().getColor(R.color.cyan));
            for (ClothingItem item : clothingItems)
            {
                if (item.isInWash())
                {
                    typeItems.add(item);
                }
            }
        }
        else
        {
            for (ClothingItem item : clothingItems)
            {
                if (item.getType().equals(sortType) && !item.isInWash())
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

    /**
     * Every time the input is changed within the search bar, run filterList
     */
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

    /**
     * Copies the complete list of items, then removes items that do not contain the search string
     *
     * @param s string search
     */
    private void filterList(String s)
    {
        filteredList = (ArrayList<ClothingItem>) typeItems.clone();
        for (ClothingItem item : typeItems)
        {
            if (!item.getName().toLowerCase().contains(s.toLowerCase()))
            {
                filteredList.remove(item);
            }
        }

        System.out.println(filteredList);
        adapter.setFilteredList(filteredList);
    }

    /**
     * Opens a color wheel, then if OK is clicked, sort the list by the closest to furthest color
     * from the color chosen. If Cancel is clicked, reset the search to the original list
     *
     * @param v button click
     */
    public void openColorPicker(View v)
    {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this,
                chosenColor, new AmbilWarnaDialog.OnAmbilWarnaListener()
        {
            @Override
            public void onCancel(AmbilWarnaDialog dialog)
            {
                ImageViewCompat.setImageTintList(colorWheelImageView, null);
                choseColor = false;
                filteredList = (ArrayList<ClothingItem>) typeItems.clone();
                adapter.setFilteredList(filteredList);
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color)
            {
                System.out.println(color);
                chosenColor = color;
                choseColor = true;
                ImageViewCompat.setImageTintList(colorWheelImageView, ColorStateList.valueOf(color));
                String hex = Integer.toHexString(color);
                System.out.println(hex);
                filteredList = (ArrayList<ClothingItem>) typeItems.clone();
                sortByColor(color, filteredList);
                adapter.setFilteredList(filteredList);
            }
        });
        colorPicker.show();
    }

    /**
     * Sorts the ArrayList 'arr' from closest to 'color' the furthest from 'color
     *
     * @param color integer representation of the color that the user inputs into the search
     * @param arr   ArrayList of clothing items that is within the type
     */
    public void sortByColor(int color, ArrayList<ClothingItem> arr)
    {
        int n = arr.size();

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (findDistance(color, arr.get(j).getColor()) < findDistance(color, arr.get(min_idx).getColor()))
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            ClothingItem temp = arr.get(min_idx);
            arr.set(min_idx, arr.get(i));
            arr.set(i, temp);
        }
    }

    /**
     * Calculates the distance between two colors color1 and color2
     *
     * @param color1 integer color 1
     * @param color2 integer color 2
     * @return The distance calculated between color1 and color2
     */
    public double findDistance(int color1, int color2)
    {
        int r1 = Color.red(color1);
        int g1 = Color.green(color1);
        int b1 = Color.blue(color1);

        int r2 = Color.red(color2);
        int g2 = Color.green(color2);
        int b2 = Color.blue(color2);

        double distance = Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));

        return distance;
    }

    /**
     * Sets on click listeners for each item of the RecyclerView, and goes to ClothingItemActivity
     * with the correct information once a RecyclerView item is clicked
     */
    public void setOnClickListener()
    {
        listener = new CTRecyclerAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent intent = new Intent(getApplicationContext(), ClothingItemActivity.class);
                if (filteredList.size() > 0)
                {
                    intent.putExtra("item", filteredList.get(position));
                }
                else
                {
                    intent.putExtra("item", typeItems.get(position));
                }
                intent.putExtra("sortType", sortType);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        };
    }

    /**
     * Sets the adapter for the recyclerview
     */
    public void setAdapter()
    {
        setOnClickListener();
        adapter = new CTRecyclerAdapter(typeItems, listener, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        CTRecyclerView.setLayoutManager(layoutManager);
        CTRecyclerView.setItemAnimator(new DefaultItemAnimator());
        CTRecyclerView.setAdapter(adapter);
    }

    /**
     * Goes to ClosetActivity
     *
     * @param v button click
     */
    public void goClosetActivity(View v)
    {
        startActivity(new Intent(this, ClosetActivity.class));
    }

    /**
     * Goes to AddItemActivity
     *
     * @param v button click
     */
    public void goAddItemActivity(View v)
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("sortType", sortType);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}