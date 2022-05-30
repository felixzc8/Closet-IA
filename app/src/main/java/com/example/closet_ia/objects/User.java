package com.example.closet_ia.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
    private String ID;
    private String name;
    private String email;
    private ArrayList<ClothingItem> clothingItems;

    public User()
    {
    }

    public User(String ID, String name, String email)
    {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.clothingItems = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "User{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", clothingItems=" + clothingItems +
                '}';
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public ArrayList<ClothingItem> getClothingItems()
    {
        return clothingItems;
    }

    public void setClothingItems(ArrayList<ClothingItem> clothingItems)
    {
        this.clothingItems = clothingItems;
    }

    public void addItem(ClothingItem item)
    {
        this.clothingItems.add(item);
    }
}
