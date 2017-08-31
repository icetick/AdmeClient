package com.example.ice_t.admeclient;

import java.io.Serializable;

/**
 * Created by ice-t on 30.08.2017.
 */

//Simple entity for news
public class Article implements Serializable {

    //Information about it
    private String imageUrl;
    private String title;
    private String description;
    private String detailsUrl;

    //Simple constructor
    public Article(String imageUrl, String title, String description, String detailsUrl)
    {
        this.description = description;
        this.imageUrl = imageUrl;
        this.title = title;
        this.detailsUrl = detailsUrl;
    }

    //If need to get information about article
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
