package com.example.artgallery_assignment1;

public class ArtPieceModel {
    private final String title;
    private final String shortDescription;
    private final String description;
    private final String image;
    private final double rating;
    private final int reviews;

    // Constructor
    public ArtPieceModel(String title, String shortDescription, String description, String image, double rating, int reviews) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.reviews = reviews;
    }

    // Getters and Setters for the fields
    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public double getRating() {
        return rating;
    }

    public int getReviews() {
        return reviews;
    }
}


