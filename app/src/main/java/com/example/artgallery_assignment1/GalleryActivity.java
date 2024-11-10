package com.example.artgallery_assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class GalleryActivity extends AppCompatActivity implements ArtPieceAdapter.OnArtPieceClickListener {

    private ArrayList<ArtPieceModel> artPieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        RecyclerView recyclerView = findViewById(R.id.recyclerGalleryView);

        // Load the art pieces from the JSON
        artPieces = loadArtPiecesFromJson();

        ArtPieceAdapter adapter = new ArtPieceAdapter(this, artPieces, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<ArtPieceModel> loadArtPiecesFromJson() {
        ArrayList<ArtPieceModel> artPieces = new ArrayList<>();

        try (InputStream inputStream = getAssets().open("art-pieces.json")) {
            String json = convertStreamToString(inputStream);
            Log.d("GalleryView", "Loaded JSON: " + json);

            // Parse the JSON into ArtPieceModel array
            Gson gson = new Gson();
            ArtPieceModel[] artPiecesArray = gson.fromJson(json, ArtPieceModel[].class);

            if (artPiecesArray != null) {
                artPieces.addAll(Arrays.asList(artPiecesArray));
            } else {
                Log.e("GalleryView", "Error: Parsed array is null.");
            }
        } catch (IOException e) {
            Log.e("GalleryView", "Error reading JSON file", e);
        } catch (JsonSyntaxException e) {
            Log.e("GalleryView", "Error parsing JSON", e);
        }

        return artPieces;
    }


    private String convertStreamToString(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }



    @Override
    public void onArtPieceClick() {
        ArtPieceModel selectedArtPiece = artPieces.get(0);

        // Convert the selected ArtPieceModel to JSON
        String artPieceJson = new Gson().toJson(selectedArtPiece);

        // Pass data to the detailed view using Intent
        Intent intent = new Intent(this, ArtPieceDetailView.class);
        intent.putExtra("artPiece", artPieceJson);
        startActivity(intent);
    }

}
