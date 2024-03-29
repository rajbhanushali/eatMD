package io.paperplane.rajb.mealapp;

public class RecipeActivity {
    private int id;
    private String title;
    private String shortdesc;
    private double rating;
    private double price;
    private int image;
    private String link;

    public RecipeActivity(int id, String title, String shortdesc, double rating, double price, int image, String link) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public String getLink(){return link;}
}