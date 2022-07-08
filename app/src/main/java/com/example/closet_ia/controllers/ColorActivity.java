package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    String type;
    String sortType;

    TextView nameTV;
    ImageView colorIV, complementIV, lightIV, darkIV, CL1IV, CL2IV, CL3IV, CL4IV;
    ConstraintLayout CL1, CL2, CL3, CL4;

    public static Integer color1;
    public static Integer color2;
    public static Integer color3;
    public static Integer color4;
    public static String type1;
    public static String type2;
    public static String type3;
    public static String type4;

    Drawable caBG1;
    Drawable caBG2;
    Drawable caBG3;
    Drawable caBG4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        item = (ClothingItem) intent.getSerializableExtra("item");
        sortType = intent.getStringExtra("sortType");
        type = item.getType();
        clothingItems = user.getClothingItems();

        nameTV = findViewById(R.id.nameTV);
        colorIV = findViewById(R.id.colorIV);
        complementIV = findViewById(R.id.complementIV);
        lightIV = findViewById(R.id.lightIV);
        darkIV = findViewById(R.id.darkIV);

        CL1 = findViewById(R.id.CL1);
        CL2 = findViewById(R.id.CL2);
        CL3 = findViewById(R.id.CL3);
        CL4 = findViewById(R.id.CL4);
        CL1IV = findViewById(R.id.CL1IV);
        CL2IV = findViewById(R.id.CL2IV);
        CL3IV = findViewById(R.id.CL3IV);
        CL4IV = findViewById(R.id.CL4IV);

        caBG1 = getResources().getDrawable(R.drawable.ca_bg1);
        caBG2 = getResources().getDrawable(R.drawable.ca_bg2);
        caBG3 = getResources().getDrawable(R.drawable.ca_bg3);
        caBG4 = getResources().getDrawable(R.drawable.ca_bg4);

        setDisplay();
    }

    public void setDisplay()
    {
        nameTV.setText(item.getName());
        ImageViewCompat.setImageTintList(colorIV, ColorStateList.valueOf(item.getColor()));
        ImageViewCompat.setImageTintList(complementIV, ColorStateList.valueOf(findComplementColor(item.getColor())));
        ImageViewCompat.setImageTintList(lightIV, ColorStateList.valueOf(findLightShade(item.getColor())));
        ImageViewCompat.setImageTintList(darkIV, ColorStateList.valueOf(findDarkShade(item.getColor())));

        if (color1 != null)
        {
            caBG1.setColorFilter(color1, PorterDuff.Mode.SRC_ATOP);
            CL1.setBackgroundDrawable(caBG1);
            setImage(type1, CL1IV);
        }
        if (color2 != null)
        {
            caBG2.setColorFilter(color2, PorterDuff.Mode.SRC_ATOP);
            CL2.setBackgroundDrawable(caBG2);
            setImage(type2, CL2IV);
        }
        if (color3 != null)
        {
            caBG3.setColorFilter(color3, PorterDuff.Mode.SRC_ATOP);
            CL3.setBackgroundDrawable(caBG3);
            setImage(type3, CL3IV);
        }
        if (color4 != null)
        {
            caBG4.setColorFilter(color4, PorterDuff.Mode.SRC_ATOP);
            CL4.setBackgroundDrawable(caBG4);
            setImage(type4, CL4IV);
        }
    }

    public int findLightShade(int color)
    {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        System.out.println("red: " + r + " green " + g + " blue: " + b);

        int cR = r + 50;
        int cG = g + 50;
        int cB = b + 50;

        if (cR > 255)
        {
            cR = 255;
        }
        if (cG > 255)
        {
            cG = 255;
        }
        if (cB > 255)
        {
            cB = 255;
        }
        int cRGB = 0xff;
        cRGB = (cRGB << 8) + cR;
        cRGB = (cRGB << 8) + cG;
        cRGB = (cRGB << 8) + cB;

        System.out.println("red: " + cR + " green: " + cG + " blue: " + cB);

        return cRGB;
    }

    public int findDarkShade(int color)
    {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        System.out.println("red: " + r + " green " + g + " blue: " + b);

        int cR = r - 50;
        int cG = g - 50;
        int cB = b - 50;

        if (cR < 0)
        {
            cR = 0;
        }
        if (cG < 0)
        {
            cG = 0;
        }
        if (cB < 0)
        {
            cB = 0;
        }
        int cRGB = 0xff;
        cRGB = (cRGB << 8) + cR;
        cRGB = (cRGB << 8) + cG;
        cRGB = (cRGB << 8) + cB;

        System.out.println("red: " + cR + " green: " + cG + " blue: " + cB);

        return cRGB;
    }

    public void setImage(String type, ImageView v)
    {
        switch (type)
        {
            case "shirts":
                v.setImageResource(R.drawable.shirt);
                break;
            case "pants":
                v.setImageResource(R.drawable.pants);
                break;
            case "shoes":
                v.setImageResource(R.drawable.shoes);
                break;
            case "outerwear":
                v.setImageResource(R.drawable.jacket);
                break;
            case "underwear & socks":
                v.setImageResource(R.drawable.underwear);
                break;
            case "dresses":
                v.setImageResource(R.drawable.dress);
                break;
            case "accessories":
                v.setImageResource(R.drawable.accessories);
                break;
        }
        ImageViewCompat.setImageTintList(v, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
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

    public void setColor(View v)
    {
        switch (v.getId())
        {
            case R.id.CL1:
                color1 = item.getColor();
                type1 = item.getType();
                caBG1.setColorFilter(color1, PorterDuff.Mode.SRC_ATOP);
                CL1.setBackgroundDrawable(caBG1);
                setImage(type1, CL1IV);

                break;
            case R.id.CL2:
                color2 = item.getColor();
                type2 = item.getType();
                caBG2.setColorFilter(color2, PorterDuff.Mode.SRC_ATOP);
                CL2.setBackgroundDrawable(caBG2);
                setImage(type2, CL2IV);
                break;
            case R.id.CL3:
                color3 = item.getColor();
                type3 = item.getType();
                caBG3.setColorFilter(color3, PorterDuff.Mode.SRC_ATOP);
                CL3.setBackgroundDrawable(caBG3);
                setImage(type3, CL3IV);
                break;
            case R.id.CL4:
                color4 = item.getColor();
                type4 = item.getType();
                caBG4.setColorFilter(color4, PorterDuff.Mode.SRC_ATOP);
                CL4.setBackgroundDrawable(caBG4);
                setImage(type4, CL4IV);
                break;
        }
    }

    public void clearItem(View v)
    {
        switch(v.getId())
        {
            case R.id.clear1:
                color1 = null;
                type1 = null;
                CL1.setBackgroundDrawable(getDrawable(R.drawable.watermelon_to_pink));
                CL1IV.setImageDrawable(getDrawable(R.drawable.transparent));
                break;
            case R.id.clear2:
                color2 = null;
                type2 = null;
                CL2.setBackgroundDrawable(getDrawable(R.drawable.watermelon_to_pink));
                CL2IV.setImageDrawable(getDrawable(R.drawable.transparent));
                break;
            case R.id.clear3:
                color3 = null;
                type3 = null;
                CL3.setBackgroundDrawable(getDrawable(R.drawable.watermelon_to_pink));
                CL3IV.setImageDrawable(getDrawable(R.drawable.transparent));
                break;
            case R.id.clear4:
                color4 = null;
                type4 = null;
                CL4.setBackgroundDrawable(getDrawable(R.drawable.watermelon_to_pink));
                CL4IV.setImageDrawable(getDrawable(R.drawable.transparent));
                break;
        }
    }

    public void goClothingItemActivity(View v)
    {
        Intent intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("user", user);
        intent.putExtra("sortType", sortType);
        startActivity(intent);
    }
}