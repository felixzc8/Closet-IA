package com.example.closet_ia.controllers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.R;

import java.util.ArrayList;

public class CTRecyclerAdapter extends RecyclerView.Adapter<CTRecyclerAdapter.CTViewHolder>
{
    private ArrayList<ClothingItem> typeItems;
    private RecyclerViewClickListener listener;
    private ClothingItem item;
    private Context context;

    public CTRecyclerAdapter(ArrayList<ClothingItem> typeItems, RecyclerViewClickListener listener, Context context)
    {
        this.typeItems = typeItems;
        this.listener = listener;
        this.context = context;
    }

    public class CTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView nameTextView, lastUsedTextView;
        private ImageView colorImageView, typeImageView;
        private CardView cardView;

        public CTViewHolder(final View v)
        {
            super(v);
            nameTextView = v.findViewById(R.id.nameTextView);
            lastUsedTextView = v.findViewById(R.id.lastUsedTextView);
            colorImageView = v.findViewById(R.id.colorImageView);
            typeImageView = v.findViewById(R.id.itemTypeImageView);
            cardView = v.findViewById(R.id.cardView);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener.onClick(view, getAdapterPosition());
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
        item = typeItems.get(position);
        String name = item.getName();
        String lastUsed = "last used: " + item.getLastUsed();
        holder.nameTextView.setText(name);
        holder.lastUsedTextView.setText(lastUsed);
        ImageViewCompat.setImageTintList(holder.colorImageView, ColorStateList.valueOf(item.getColor()));
        switch (item.getType())
        {
            case "shirts":
                holder.typeImageView.setImageResource(R.drawable.shirt);
                break;
            case "pants":
                holder.typeImageView.setImageResource(R.drawable.pants);
                break;
            case "shoes":
                holder.typeImageView.setImageResource(R.drawable.shoes);
                break;
            case "outerwear":
                holder.typeImageView.setImageResource(R.drawable.jacket);
                break;
            case "underwear & socks":
                holder.typeImageView.setImageResource(R.drawable.underwear);
                break;
            case "dresses":
                holder.typeImageView.setImageResource(R.drawable.dress);
                break;
            case "accessories":
                holder.typeImageView.setImageResource(R.drawable.accessories);
                break;
        }
        if (item.isInWash())
        {
            Drawable roundBG = context.getResources().getDrawable(R.drawable.round_bg);
            roundBG.setColorFilter(context.getColor(R.color.cyan), PorterDuff.Mode.SRC_ATOP);
            holder.cardView.setBackgroundDrawable(roundBG);
        }
    }

    @Override
    public int getItemCount()
    {
        return typeItems.size();
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }

    public void setFilteredList(ArrayList<ClothingItem> filteredList)
    {
        this.typeItems = filteredList;
        notifyDataSetChanged();
    }
}
