package com.example.artgallery_assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArtPieceAdapter extends RecyclerView.Adapter<ArtPieceAdapter.ArtPieceViewHolder> {

    private final Context context;
    private final ArrayList<ArtPieceModel> artPieces;
    private final OnArtPieceClickListener onArtPieceClickListener;

    public ArtPieceAdapter(Context context, ArrayList<ArtPieceModel> artPieces, OnArtPieceClickListener listener) {
        this.context = context;
        this.artPieces = artPieces;
        this.onArtPieceClickListener = listener;
    }

    @NonNull
    @Override
    public ArtPieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_art_piece, parent, false);
        return new ArtPieceViewHolder(itemView, onArtPieceClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtPieceViewHolder holder, int position) {
        // Get the art piece at the given position
        ArtPieceModel artPiece = artPieces.get(position);

        // Set the data into the views
        holder.titleTextView.setText(artPiece.getTitle());
        holder.descriptionTextView.setText(artPiece.getShortDescription());

        System.out.println("Binding ArtPiece: " + artPiece.getTitle());
        Toast.makeText(context, "Loaded: " + artPiece.getTitle(), Toast.LENGTH_SHORT).show();

        // Use Glide to load the image dynamically
        Glide.with(context)
                .load(artPiece.getImage())  // Load the image URL from the model
                .placeholder(R.drawable.fintechlogo)  // Placeholder image while loading
                .error(R.drawable.fintechlogo)        // Error image if the URL is invalid
                .into(holder.artImageView);
    }


    @Override
    public int getItemCount() {
        return artPieces.size();
    }

    // ViewHolder class to hold the item views
    public static class ArtPieceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView, descriptionTextView, reviewsTextView;
        ImageView artImageView;
        OnArtPieceClickListener onArtPieceClickListener;

        public ArtPieceViewHolder(@NonNull View itemView, OnArtPieceClickListener listener) {
            super(itemView);

            // Bind the views
            titleTextView = itemView.findViewById(R.id.artTitle);
            descriptionTextView = itemView.findViewById(R.id.artShortDescription);
            reviewsTextView = itemView.findViewById(R.id.reviewCount);
            artImageView = itemView.findViewById(R.id.artImage);

            // Set click listener
            this.onArtPieceClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Call the click listener for both the item and the right caret icon
            if (onArtPieceClickListener != null) {
                onArtPieceClickListener.onArtPieceClick(getAdapterPosition());
            }
        }
    }

    // Interface for item click handling
    public interface OnArtPieceClickListener {
        void onArtPieceClick(int position);
    }
}

