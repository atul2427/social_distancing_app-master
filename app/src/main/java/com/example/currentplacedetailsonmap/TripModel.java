package com.example.currentplacedetailsonmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TripModel {

    private List<ReadingModel> tripReadings;
    private String tripId;
    private Date startTime;
    private Date endTime;
    private double score;

    public TripModel()
    {
        this.setNumDevices(0);
    }

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }

    private int numDevices;


    public List<ReadingModel> getTripReadings() {
        return tripReadings;
    }

    public void setTripReadings(List<ReadingModel> tripReadings) {
        this.tripReadings = tripReadings;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public static String serialize(TripModel m)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(m.getTripId());
        sb.append("$");
        for(ReadingModel r : m.getTripReadings())
        {
            sb.append(ReadingModel.serialize(r));
            sb.append("_");
        }
        sb.append("$");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZa");
        sb.append(formatter.format(m.getStartTime()));
        sb.append("$");
        sb.append(formatter.format(m.getEndTime()));
        sb.append("$");
        sb.append(m.getScore());
        sb.append("$");
        return sb.toString();
    }

    public static TripModel deserialize(String s)
    {

        try {
            TripModel m = new TripModel();
            Scanner sc = new Scanner(s);
            sc.useDelimiter("$");
            m.setTripId(sc.next());
            String listString = sc.next();
            Scanner sct = new Scanner(listString);
            sct.useDelimiter("_");
            List<ReadingModel> list = new ArrayList<>();
            while(sct.hasNext())
            {
                list.add(ReadingModel.deserialize(sc.next()));
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            m.setStartTime(formatter.parse(sc.next()));
            m.setEndTime(formatter.parse(sc.next()));
            m.setScore(sc.nextInt());
            return m;

        } catch (ParseException e) {
            e.getMessage();
        }
        return null;
    }



}
