package com.example.artgallery_assignment1;

import java.util.ArrayList;

public class ArtPieceModel {
    private String title;
    private String description;
    private String shortDescription;
    private int starRating;
    private int reviews;
    private String imageUrl;

    // Constructor
    public ArtPieceModel(String title, String description, String shortDescription, String imageUrl, int starRating, int reviews) {
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
        this.starRating = starRating;
        this.reviews = reviews;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getStarRating() {
        return starRating;
    }

    public int getReviews() {
        return reviews;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}

