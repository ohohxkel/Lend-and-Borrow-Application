package com.example.labapp;

import java.util.List;

public class Items {

    public List<String> items;

    public Items(){}


    public Items(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
