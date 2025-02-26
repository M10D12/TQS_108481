package com.exemplo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    public int id;
    public String IMAGE;
    public String description;
    public double price;
    public String title;
    public String category;

    public Product(int id, String IMAGE, String description, double price, String title, String category) {
        this.id = id;
        this.IMAGE = IMAGE;
        this.description = description;
        this.price = price;
        this.title = title;
        this.category = category;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("image")
    public String getIMAGE() {
        return IMAGE;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    
}
