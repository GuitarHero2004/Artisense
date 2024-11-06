package com.example.artgallery_assignment1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_view);

        RecyclerView recyclerView = findViewById(R.id.galleryView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Fetch ArtPiece component into list of ArtPieces in GalleryView
        ArtPieceModel adapter = new ArtPieceModel(ArtPieceView.getArtPieces(), this);
        recyclerView.setAdapter(adapter);

        // Event Listener
        adapter.setOnItemClickListener(new ArtPieceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArtPieceModel artPiece = ArtPieceView.getArtPieces().get(position);
                Intent intent = new Intent(GalleryView.this, ArtPieceDetailView.class);
                intent.putExtra("title", artPiece.getTitle());
                intent.putExtra("description", artPiece.getDescription());
                intent.putExtra("shortDescription", artPiece.getShortDescription());
                intent.putExtra("imageUrl", artPiece.getImageUrl());
                intent.putExtra("starRating", artPiece.getStarRating());
                intent.putExtra("reviews", artPiece.getReviews());
                startActivity(intent);
            }
        });
    }
}