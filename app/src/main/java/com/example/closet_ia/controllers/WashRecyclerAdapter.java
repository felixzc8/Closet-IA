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
    private RecyclerViewClickListener listener;

    public WashRecyclerAdapter(ArrayList<ClothingItem> clothingItems, RecyclerViewClickListener listener)
    {
        this.clothingItems = clothingItems;
        this.listener = listener;
    }

    public class WashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView washNameTextView, lastUsedTextView;
        private ImageView colorImageView;

        public WashViewHolder(final View v)
        {
            super(v);
            washNameTextView = v.findViewById(R.id.washNameTextView);

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
        String name = clothingItems.get(position).getName();
        holder.washNameTextView.setText(name);
    }

    @Override
    public int getItemCount()
    {
        return clothingItems.size();
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }
}
