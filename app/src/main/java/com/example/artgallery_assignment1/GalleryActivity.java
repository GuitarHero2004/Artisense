package com.example.artgallery_assignment1;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GalleryActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        // Initialize the Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Set initial fragment to Famous Art if no saved instance state
        if (savedInstanceState == null) {
            replaceFragment(new FamousArtFragment());
        }

        // Set up Bottom Navigation listener
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            int itemId = item.getItemId();

            // Choose the fragment based on selected item
            if (itemId == R.id.famous_art) {
                selectedFragment = new FamousArtFragment();
            } else if (itemId == R.id.gallery_map_fragment_view) {
                selectedFragment = new GalleryMapFragment();
            } else if (itemId == R.id.art_quiz) {
                selectedFragment = new ArtQuizFragment();
            } else {
                return false;
            }

            // Replace fragment only if it’s not already displayed
            replaceFragment(selectedFragment);
            return true;
        });
    }

    /**
     * Replaces the current fragment with the specified fragment.
     * Checks if the new fragment is different from the current one.
     */
    private void replaceFragment(@NonNull Fragment fragment) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.gallery_fragment_container);

        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return; // Avoid redundant fragment replacements
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.gallery_fragment_container, fragment)
                .commit();
    }
}
