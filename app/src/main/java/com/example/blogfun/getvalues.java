package com.example.blogfun;

public class getvalues {
    private String title;
    private String description;
    private String imageurl;
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public getvalues(String title, String description, String imageurl,String from) {
        this.title = title;
        this.description = description;
        this.imageurl = imageurl;
        this.from=from;
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
    public getvalues()
    {

    }




}
