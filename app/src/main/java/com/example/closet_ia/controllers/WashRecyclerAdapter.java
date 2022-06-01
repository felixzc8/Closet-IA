package com.example.closet_ia.controllers;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;

import java.util.ArrayList;

public class WashRecyclerAdapter extends RecyclerView.Adapter<WashRecyclerAdapter.WashViewHolder>
{
    private ArrayList<ClothingItem> washingItems;
    private RecyclerViewClickListener listener;
    private ClothingItem item;

    public WashRecyclerAdapter(ArrayList<ClothingItem> clothingItems, RecyclerViewClickListener listener)
    {
        this.washingItems = clothingItems;
        this.listener = listener;
    }

    public class WashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView washNameTextView, timesWashedTextView;
        private ImageView colorImageView;

        public WashViewHolder(final View v)
        {
            super(v);
            washNameTextView = v.findViewById(R.id.washNameTextView);
            timesWashedTextView = v.findViewById(R.id.timesWashedTextView);
            colorImageView = v.findViewById(R.id.WashColorImageView);

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
    public WashRecyclerAdapter.WashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.washing_items, parent, false);
        return new WashRecyclerAdapter.WashViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WashRecyclerAdapter.WashViewHolder holder, int position)
    {
        item = washingItems.get(position);
        String name = item.getName();
        String timesWashed = "times washed: " + item.getTimesWashed();
        holder.washNameTextView.setText(name);
        holder.timesWashedTextView.setText(timesWashed);
        ImageViewCompat.setImageTintList(holder.colorImageView, ColorStateList.valueOf(item.getColor()));
    }

    @Override
    public int getItemCount()
    {
        return washingItems.size();
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }

    public void setFilteredList(ArrayList<ClothingItem> filteredList)
    {
        this.washingItems = filteredList;
        notifyDataSetChanged();
    }
}
