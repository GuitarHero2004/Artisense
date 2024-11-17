package com.example.artgallery_assignment1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.artgallery_assignment1.Model.ArtPieceModel;
import com.example.artgallery_assignment1.R;

import java.util.ArrayList;

public class ArtPieceAdapter extends RecyclerView.Adapter<ArtPieceAdapter.ArtPieceViewHolder> {

    private final Context context;
    private ArrayList<ArtPieceModel> artPieces;
    private final ArrayList<ArtPieceModel> allArtPieces;
    private final OnArtPieceClickListener onArtPieceClickListener;

    public ArtPieceAdapter(Context context, ArrayList<ArtPieceModel> artPieces, OnArtPieceClickListener listener) {
        this.context = context;
        this.artPieces = new ArrayList<>(artPieces);
        this.allArtPieces = new ArrayList<>(artPieces);
        this.onArtPieceClickListener = listener;
    }

    @NonNull
    @Override
    public ArtPieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_art_piece, parent, false);
        return new ArtPieceViewHolder(itemView, onArtPieceClickListener);
    }

    public ArrayList<ArtPieceModel> getArtPieces() {
        return artPieces;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArtPieceViewHolder holder, int position) {
        // Get the art piece at the given position
        ArtPieceModel artPiece = artPieces.get(position);

        // Set the data into the views
        holder.titleTextView.setText(artPiece.getTitle());
        holder.authorTextView.setText(artPiece.getAuthor());
        holder.shortDescriptionTextView.setText(artPiece.getShortDescription());
        holder.starsCountTextView.setText(String.valueOf(artPiece.getRating()));
        holder.reviewsTextView.setText("(" + artPiece.getReviews() + " reviews)");

//        System.out.println("Binding ArtPiece: " + artPiece.getTitle());
//        Toast.makeText(context, "Loaded: " + artPiece.getTitle(), Toast.LENGTH_SHORT).show();

        // Use Glide to load the image dynamically
        @SuppressLint("DiscouragedApi") int imageResId = context.getResources().getIdentifier(artPiece.getImage(), "drawable", context.getPackageName());
        if (imageResId != 0) {  // Check if resource ID is valid
            Glide.with(context)
                    .load(imageResId)  // Load the drawable resource ID
                    .diskCacheStrategy(DiskCacheStrategy.NONE)  // Disable caching
                    .placeholder(R.drawable.fintechlogo)  // Placeholder image while loading
                    .error(R.drawable.fintechlogo)  // Error image if the resource ID is invalid
                    .into(holder.artImageView);
        } else {
            // If imageResId is 0, the image wasn't found; set error drawable directly
            holder.artImageView.setImageResource(R.drawable.fintechlogo);
        }
    }


    @Override
    public int getItemCount() {
        return artPieces.size();
    }

    // ViewHolder class to hold the item views
    public static class ArtPieceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView, authorTextView, shortDescriptionTextView, starsCountTextView, reviewsTextView;
        ImageView artImageView;
        OnArtPieceClickListener onArtPieceClickListener;

        public ArtPieceViewHolder(@NonNull View itemView, OnArtPieceClickListener listener) {
            super(itemView);

            // Bind the views
            titleTextView = itemView.findViewById(R.id.artTitle);
            authorTextView = itemView.findViewById(R.id.artAuthor);
            shortDescriptionTextView = itemView.findViewById(R.id.artShortDescription);
            starsCountTextView = itemView.findViewById(R.id.starsCount);
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

    // Filter method
    @SuppressLint("NotifyDataSetChanged")
    public void filterByQuery(String query) {
        if (query.isEmpty()) {
            artPieces = new ArrayList<>(allArtPieces);
        } else {
            ArrayList<ArtPieceModel> filteredList = new ArrayList<>();
            for (ArtPieceModel artPiece : allArtPieces) {
                if (artPiece.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                        artPiece.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(artPiece);
                }
            }
            artPieces = filteredList;
        }
        notifyDataSetChanged();
    }



    // Interface for item click handling
    public interface OnArtPieceClickListener {
        void onArtPieceClick(int position);
    }
}

