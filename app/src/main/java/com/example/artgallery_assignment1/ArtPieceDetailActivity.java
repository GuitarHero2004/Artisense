package com.example.artgallery_assignment1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.artgallery_assignment1.Model.ArtPieceModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class ArtPieceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_piece_detail);

        // Retrieve the art piece JSON string from the intent
        String artPieceJson = getIntent().getStringExtra("artPiece");
        if (artPieceJson == null) {
            // Handle the case where no art piece is passed
            throw new IllegalArgumentException("Art piece data not found in intent");
        }

        // Deserialize the JSON string into an ArtPieceModel object
        ArtPieceModel selectedArtPiece = new Gson().fromJson(artPieceJson, ArtPieceModel.class);

        // Set up ViewPager and TabLayout for displaying fragments
        setupViewPagerAndTabs(selectedArtPiece);
    }

    /**
     * Sets up the ViewPager and TabLayout with fragments.
     * @param selectedArtPiece The selected art piece to pass to the fragments.
     */
    private void setupViewPagerAndTabs(ArtPieceModel selectedArtPiece) {
        // Initialize ViewPager and TabLayout
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Create the TabAdapter and add fragments
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        // Add the DetailTabFragment for the first tab
        tabAdapter.addFragment(new DetailTabFragment(), "Details");

        // Add the ExtraTabFragment for the second tab with the art piece data
        MapTabFragment mapTabFragment = new MapTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedArtPiece", selectedArtPiece); // Pass the art piece data
        mapTabFragment.setArguments(bundle);
        tabAdapter.addFragment(mapTabFragment, "Location");

        // Set up the ViewPager with the TabAdapter and attach the TabLayout
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
