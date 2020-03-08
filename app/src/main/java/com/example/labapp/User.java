package com.example.labapp;

public class User {


    public String name, email, studentNumber, yearAndSection, userID;

    public User(){

    }


    public User(String email, String name, String studentNumber, String userID, String yearAndSection) {
        this.name = name;
        this.email = email;
        this.studentNumber = studentNumber;
        this.userID= userID;
        this.yearAndSection = yearAndSection;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getYearAndSection() {
        return yearAndSection;
    }

    public void setYearAndSection(String yearAndSection) {
        this.yearAndSection = yearAndSection;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }




}
