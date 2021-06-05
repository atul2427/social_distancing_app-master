package com.example.currentplacedetailsonmap;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private String username;
    private String id;
    List<TripModel> trips;
    private int age;
    private String gender;
    private String condition;
    private String occupation;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }


    private double score;
    public UserModel()
    {
        this.setTrips(new ArrayList<TripModel>());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TripModel> getTrips() {
        return trips;
    }

    public void setTrips(List<TripModel> trips) {
        this.trips = trips;
    }
}
