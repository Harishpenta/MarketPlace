package com.hashrail.marketplace.GridListView;

/**
 * Created by Dreamworld Solutions on 15-7-2016.
 */
public class productGrid {
    private String name;
    private int numOfProducts;
    private int thumbnail;

    public productGrid(String name, int numOfProducts, int thumbnail) {
        this.name = name;
        this.numOfProducts = numOfProducts;
        this.thumbnail = thumbnail;
    }

    public productGrid() {
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
