package com.example.artgallery_assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artgallery_assignment1.Model.ArtPieceModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class FamousArtFragment extends Fragment implements ArtPieceAdapter.OnArtPieceClickListener {

    private ArtPieceAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_famous_art, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerGalleryView);
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Title or Author...");
        searchView.setIconified(false);
        searchView.clearFocus();

        // Load art pieces from JSON
        ArrayList<ArtPieceModel> artPieces = loadArtPiecesFromJson();

        // Set up adapter and RecyclerView
        adapter = new ArtPieceAdapter(requireContext(), artPieces, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Set up search view listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterByQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterByQuery(newText);
                return false;
            }
        });
    }

    private ArrayList<ArtPieceModel> loadArtPiecesFromJson() {
        ArrayList<ArtPieceModel> artPieces = new ArrayList<>();

        try (InputStream inputStream = requireContext().getAssets().open("art-pieces.json")) {
            String json = convertStreamToString(inputStream);
            Log.d("FamousArtFragment", "Loaded JSON: " + json);

            Gson gson = new Gson();
            ArtPieceModel[] artPiecesArray = gson.fromJson(json, ArtPieceModel[].class);

            if (artPiecesArray != null) {
                artPieces.addAll(Arrays.asList(artPiecesArray));
            } else {
                Log.e("FamousArtFragment", "Error: Parsed array is null.");
            }
        } catch (IOException e) {
            Log.e("FamousArtFragment", "Error reading JSON file", e);
        } catch (JsonSyntaxException e) {
            Log.e("FamousArtFragment", "Error parsing JSON", e);
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
    public void onArtPieceClick(int position) {
        ArtPieceModel selectedArtPiece = adapter.getArtPieces().get(position);

        String artPieceJson = new Gson().toJson(selectedArtPiece);

        Intent intent = new Intent(requireContext(), ArtPieceDetailActivity.class);
        intent.putExtra("artPiece", artPieceJson);
        startActivity(intent);
    }
}
