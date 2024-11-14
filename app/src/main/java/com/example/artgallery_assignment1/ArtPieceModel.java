package com.example.artgallery_assignment1;

public class ArtPieceModel {
    private final String title;
    private final String author;
    private final String shortDescription;
    private final String description;
    private final String paintedDate;
    private final String image;
    private final double rating;
    private final int reviews;
    private final String style;
    private final String technique;
    private final String genre;

    // Constructor
    public ArtPieceModel(String title, String author, String shortDescription, String description, String paintedDate, String image, double rating, int reviews, String style, String technique, String genre) {
        this.title = title;
        this.author = author;
        this.shortDescription = shortDescription;
        this.description = description;
        this.paintedDate = paintedDate;
        this.image = image;
        this.rating = rating;
        this.reviews = reviews;
        this.style = style;
        this.technique = technique;
        this.genre = genre;
    }

    // Getters and Setters for the fields
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }
    public String getPaintedDate() {
        return paintedDate;
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

    public String getStyle() {
        return style;
    }

    public String getTechnique() {
        return technique;
    }

    public String getGenre() {
        return genre;
    }
}


