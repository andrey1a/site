package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Country implements Serializable {
    private int id;
    private String name;
    private Float area;

    public Country(int id, String name, Float area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassCastException, ClassNotFoundException {
       in.defaultReadObject();

    }
}
