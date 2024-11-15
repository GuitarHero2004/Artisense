package com.example.artgallery_assignment1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GalleryMapFragment extends Fragment implements OnMapReadyCallback {

    private ArrayList<ArtPieceModel> artPieceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_map, container, false);

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.gallery_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("GalleryMapFragment", "Map fragment not found");
        }

        // Load the art pieces from the JSON
        loadArtPiecesFromJSON();

        return view;
    }

    /**
     * Loads art pieces from a JSON file in the assets folder.
     */
    private void loadArtPiecesFromJSON() {
        try {
            InputStream is = requireContext().getAssets().open("art-pieces.json");
            InputStreamReader reader = new InputStreamReader(is);
            Type artPieceListType = new TypeToken<ArrayList<ArtPieceModel>>(){}.getType();
            Gson gson = new Gson();
            artPieceList = gson.fromJson(reader, artPieceListType);
            reader.close();
        } catch (Exception e) {
            Log.e("ExtraTabFragment", "Error reading JSON file", e);
        }
    }

    /**
     * Handles the map when it's ready, adding markers for art pieces.
     * @param googleMap The GoogleMap instance to manipulate.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (artPieceList != null && !artPieceList.isEmpty()) {
            // You could loop through the list of art pieces if needed
            for (ArtPieceModel artPiece : artPieceList) {
                if (artPiece.getLocation() != null) {
                    LocationModel location = artPiece.getLocation();

                    // Create LatLng object for the location
                    LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Add a marker on the map at the location
                    googleMap.addMarker(new MarkerOptions()
                            .position(locationLatLng)
                            .title(location.getName()));

                    // Move the camera to the last marker position
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 5f));
                }
            }
        } else {
            Log.e("GalleryMapFragment", "No art pieces available or JSON not loaded");
        }
    }
}
