package com.hashrail.marketplace.RecylcerViewList;

/**
 * Created by Dreamworld Solutions on 15-7-2016.
 */
public class product {
    private String name;
    private int numOfProducts;
    private String thumbnail;

    public product(String name, int numOfProducts, String thumbnail) {
        this.name = name;
        this.numOfProducts = numOfProducts;
        this.thumbnail = thumbnail;
    }

    public product() {
    }

    public product(String thumbnail) {
        this.thumbnail = thumbnail;
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


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
