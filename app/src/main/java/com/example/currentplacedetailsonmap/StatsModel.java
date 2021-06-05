package com.example.currentplacedetailsonmap;

public class StatsModel {

    private double agePercent;
    private double genderPercent;
    private double conditionPercent;

    public double getCumScore() {
        return cumScore;
    }

    public void setCumScore(double cumScore) {
        this.cumScore = cumScore;
    }

    private double cumScore;
    public double getOccupationPercent() {
        return occupationPercent;
    }

    public void setOccupationPercent(double occupationPercent) {
        this.occupationPercent = occupationPercent;
    }

    private double occupationPercent;

    public StatsModel()
    {
        agePercent = 0.0;
        genderPercent = 0.0;
        conditionPercent = 0.0;
    }
    public double getAgePercent() {
        return agePercent;
    }

    public void setAgePercent(double agePercent) {
        this.agePercent = agePercent;
    }

    public double getGenderPercent() {
        return genderPercent;
    }

    public void setGenderPercent(double genderPercent) {
        this.genderPercent = genderPercent;
    }

    public double getConditionPercent() {
        return conditionPercent;
    }

    public void setConditionPercent(double conditionPercent) {
        this.conditionPercent = conditionPercent;
    }
}