package com.example.artgallery_assignment1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class ArtPieceView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_piece);

        // Retrieve the art piece from the Intent
        String artPieceJson = getIntent().getStringExtra("artPiece");

        if (artPieceJson != null) {
            ArtPieceModel artPiece = new Gson().fromJson(artPieceJson, ArtPieceModel.class);

            TextView titleTextView = findViewById(R.id.artTitle);
            TextView descriptionTextView = findViewById(R.id.artShortDescription);
            TextView reviewsTextView = findViewById(R.id.reviewCount);

            titleTextView.setText(artPiece.getTitle());
            descriptionTextView.setText(artPiece.getDescription());
            reviewsTextView.setText(String.valueOf(artPiece.getReviews()));

            ImageView artImageView = findViewById(R.id.artImage);
            Glide.with(this)
                    .load(artPiece.getImage())
                    .placeholder(R.drawable.fintechlogo)
                    .error(R.drawable.fintechlogo)
                    .into(artImageView);
        } else {
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        // You can display an error message here, e.g., using a Toast or a TextView.
        // For example:
        Toast.makeText(this, "Error loading art piece data", Toast.LENGTH_SHORT).show();
    }
}
