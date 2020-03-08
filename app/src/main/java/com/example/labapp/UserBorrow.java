package com.example.labapp;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class UserBorrow {

    public UserBorrow(){}

    public boolean returned;
    public Timestamp borrowedDate;
    public List<String> items;




    public UserBorrow(Timestamp borrowedDate, boolean returned, List<String> items) {
        this.returned = returned;
        this.borrowedDate = borrowedDate;
        this.items = items;
    }


    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Timestamp getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Timestamp borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }



}
