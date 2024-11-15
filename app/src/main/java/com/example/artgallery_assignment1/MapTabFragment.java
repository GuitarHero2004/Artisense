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

public class MapTabFragment extends Fragment implements OnMapReadyCallback {

    private ArtPieceModel selectedArtPiece;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_tab, container, false);

        // Retrieve the passed ArtPieceModel from arguments
        if (getArguments() != null) {
            selectedArtPiece = (ArtPieceModel) getArguments().getSerializable("selectedArtPiece");
        }

        // Initialize the map and set up the callback
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("ExtraTabFragment", "Map fragment not found");
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (selectedArtPiece != null && selectedArtPiece.getLocation() != null) {
            LocationModel location = selectedArtPiece.getLocation();
            LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            // Add a marker for the selected art piece's location
            googleMap.addMarker(new MarkerOptions()
                    .position(locationLatLng)
                    .snippet(location.getAddress())
                    .title(location.getName()));

            // Move the camera to focus on the marker
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15f));
        } else {
            Log.e("ExtraTabFragment", "No location available for the selected art piece");
        }
    }
}
