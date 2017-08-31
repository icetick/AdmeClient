package com.example.ice_t.admeclient;

/**
 * Created by ice-t on 30.08.2017.
 */

public class Article {

    private String imageUrl;
    private String title;
    private String description;
    private String detailsUrl;

    public Article(String imageUrl, String title, String description, String detailsUrl)
    {
        this.description = description;
        this.imageUrl = imageUrl;
        this.title = title;
        this.detailsUrl = detailsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDetailsUrl() { return detailsUrl; }
}
