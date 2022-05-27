package com.example.closet_ia.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.R;

import java.util.ArrayList;

public class CTRecyclerAdapter extends RecyclerView.Adapter<CTRecyclerAdapter.CTViewHolder>
{
    private ArrayList<ClothingItem>  clothingItems;

    public CTRecyclerAdapter(ArrayList<ClothingItem> clothingItems)
    {
        this.clothingItems = clothingItems;
    }

    public class CTViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nameTextView, lastUsedTextView;
        private ImageView  colorImageView;

        public CTViewHolder(final View v)
        {
            super(v);
            nameTextView = v.findViewById(R.id.nameTextView);
            lastUsedTextView = v.findViewById(R.id.lastUsedTextView);
            colorImageView = v.findViewById(R.id.colorImageView);
        }
    }
    @NonNull
    @Override
    public CTRecyclerAdapter.CTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_items, parent, false);
        return new CTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CTRecyclerAdapter.CTViewHolder holder, int position)
    {
        String name = clothingItems.get(position).getName();
        holder.nameTextView.setText(name);
    }

    @Override
    public int getItemCount()
    {
        return clothingItems.size();
    }
}
