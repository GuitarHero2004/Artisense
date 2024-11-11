package com.example.artgallery_assignment1;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetailTabFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_tab, container, false);

        // Retrieve JSON data
        String artPieceJson = getActivity().getIntent().getStringExtra("artPiece");

        if (artPieceJson != null) {
            ArtPieceModel artPiece = new Gson().fromJson(artPieceJson, ArtPieceModel.class);

            TextView titleTextView = view.findViewById(R.id.artTitle);
            TextView descriptionTextView = view.findViewById(R.id.artDescription);
            TextView authorTextView = view.findViewById(R.id.artAuthor);
            TextView paintedDateTextView = view.findViewById(R.id.artPaintedDate);
            ImageView artImageView = view.findViewById(R.id.artImage);

            titleTextView.setText(artPiece.getTitle());
            descriptionTextView.setText(artPiece.getDescription());
            authorTextView.setText("By: " + artPiece.getAuthor());
            paintedDateTextView.setText("Painted in: " + artPiece.getPaintedDate());

            int imageResId = getResources().getIdentifier(artPiece.getImage(), "drawable", getActivity().getPackageName());
            if (imageResId != 0) {
                Glide.with(this)
                        .load(imageResId)
                        .placeholder(R.drawable.fintechlogo)
                        .error(R.drawable.fintechlogo)
                        .into(artImageView);
            } else {
                artImageView.setImageResource(R.drawable.fintechlogo);
            }
        } else {
            showErrorMessage();
        }

        return view;
    }

    private void showErrorMessage() {
        Toast.makeText(getContext(), "Error loading art piece data", Toast.LENGTH_SHORT).show();
    }

}