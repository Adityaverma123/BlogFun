package com.example.blogfun;

import android.widget.Button;

public class getfeed {
    private String title;
    private String description;
    private String imageurl;
    private String imagename;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public getfeed(String imagename) {
        this.imagename = imagename;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public getfeed(String title, String description, String imageurl) {
        this.title = title;
        this.description = description;
        this.imageurl = imageurl;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public getfeed()
    {

    }
}

