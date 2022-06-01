package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.objects.User;

import java.util.ArrayList;

public class ColorActivity extends AppCompatActivity
{
    User user;
    ClothingItem item;
    ArrayList<ClothingItem> clothingItems;

    TextView nameTV;
    ImageView colorIV, complementIV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        item = (ClothingItem) intent.getSerializableExtra("item");
        clothingItems = user.getClothingItems();

        nameTV = findViewById(R.id.nameTV);
        colorIV = findViewById(R.id.colorIV);
        complementIV = findViewById(R.id.complementIV);

        setDisplay();
    }

    public void setDisplay()
    {
        nameTV.setText(item.getName());
        ImageViewCompat.setImageTintList(colorIV, ColorStateList.valueOf(item.getColor()));
        ImageViewCompat.setImageTintList(complementIV, ColorStateList.valueOf(findComplementColor(item.getColor())));
    }

    public int findComplementColor(int color)
    {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        System.out.println("red: " + r + " green " + g + " blue: " + b);

        int cR = 255 - r;
        int cG = 255 - g;
        int cB = 255 - b;

        int cRGB = 0xff;
        cRGB = (cRGB << 8) + cR;
        cRGB = (cRGB << 8) + cG;
        cRGB = (cRGB << 8) + cB;

        System.out.println("red: " + cR + " green: " + cG + " blue: " + cB);

        return cRGB;
    }

    public int findClosestColor(int color, ArrayList<Integer> colors)
    {
        Double closestDistance = findDistance(color, colors.get(0));
        int closestColor = colors.get(0);

        for (int c : colors)
        {
            if (findDistance(color, c) < closestDistance)
            {
                closestDistance = findDistance(color, c);
                closestColor = c;
            }
        }

        return closestColor;
    }

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

    public void goClothingItemActivity(View v)
    {
        Intent intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("user", user);
        intent.putExtra("origin", "ClothingTypeActivity");
        startActivity(intent);
    }
}