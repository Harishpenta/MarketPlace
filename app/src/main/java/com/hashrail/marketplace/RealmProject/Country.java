package com.hashrail.marketplace.RealmProject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dreamworld Solutions on 25-7-2016.
 */
public class Country extends RealmObject {

    private String name;
    private int population;
    @PrimaryKey
    private String code;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}