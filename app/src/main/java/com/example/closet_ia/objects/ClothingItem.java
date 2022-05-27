package com.example.closet_ia.objects;

import java.io.Serializable;

public class ClothingItem implements Serializable
{
    private String ID;
    private String type;
    private String name;
    private int color;
    private String lastUsed;
    private String datePurchased;
    private int timesWashed;


    public ClothingItem()
    {
    }

    public ClothingItem(String ID, String type, String name, int color, String datePurchased)
    {
        this.ID = ID;
        this.type = type;
        this.name = name;
        this.color = color;
        this.lastUsed = "";
        this.datePurchased = datePurchased;
        this.timesWashed = 0;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public int getTimesWashed()
    {
        return timesWashed;
    }

    public void setTimesWashed(int timesWashed)
    {
        this.timesWashed = timesWashed;
    }

    public String getLastUsed()
    {
        return lastUsed;
    }

    public void setLastUsed(String dateLastWorn)
    {
        this.lastUsed = dateLastWorn;
    }

    public String getDatePurchased()
    {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased)
    {
        this.datePurchased = datePurchased;
    }
}
