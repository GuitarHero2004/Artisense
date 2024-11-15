package com.example.artgallery_assignment1;

public class LocationModel {
    private String name;        // Location name (e.g., "Louvre Museum, Paris")
    private double latitude;    // Latitude of the location
    private double longitude;   // Longitude of the location

    // Constructor
    public LocationModel(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

