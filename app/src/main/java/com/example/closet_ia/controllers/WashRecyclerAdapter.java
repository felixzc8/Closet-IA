package com.example.closet_ia.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;

import java.util.ArrayList;

public class WashRecyclerAdapter extends RecyclerView.Adapter<WashRecyclerAdapter.WashViewHolder>
{
    private ArrayList<ClothingItem> clothingItems;

    public WashRecyclerAdapter(ArrayList<ClothingItem> clothingItems)
    {
        this.clothingItems = clothingItems;
    }

    public class WashViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nameTextView, lastUsedTextView;
        private ImageView colorImageView;

        public WashViewHolder(final View view)
        {
            super(view);

        }

    }
    @NonNull
    @Override
    public WashRecyclerAdapter.WashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_items, parent, false);
        return new WashRecyclerAdapter.WashViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WashRecyclerAdapter.WashViewHolder holder, int position)
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
