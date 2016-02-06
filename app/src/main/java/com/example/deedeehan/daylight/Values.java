package com.example.deedeehan.daylight;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/6/2016.
 */
public class Values
{
    ArrayList<Double> latitude = new ArrayList();
    ArrayList<Double> longitude = new ArrayList();
    ArrayList<String> type = new ArrayList();
    ArrayList<Double> rating = new ArrayList();
    ArrayList<ArrayList<String>> comments = new ArrayList();

    public void addValue(double lati, double longi, String typ,
                         double rati, ArrayList<String> comm)
    {
        latitude.add(lati);
        longitude.add(longi);
        type.add(typ);
        rating.add(rati);
        comments.add(comm);
    }

    public Double accessLatitude(int i)
    {
        return latitude.get(i);
    }
    public Double accessLongitude(int i)
    {
        return longitude.get(i);
    }
    public String accessType(int i)
    {
        return type.get(i);
    }
    public Double accessRating(int i)
    {
        return rating.get(i);
    }
    public ArrayList<String> accessComments(int i)
    {
        return comments.get(i);
    }

    public void addValueMap(double lati, double longi, String typ,
                         double rati, String comm)
    {
        latitude.add(lati);
        longitude.add(longi);
        type.add(typ);
        rating.add(rati);
        ArrayList<String> x = new ArrayList<>();
        x.add(comm);
        comments.add(x);
    }

    public int findLength()
    {
        return latitude.size();
    }

    public void clear()
    {
        latitude.clear();
        longitude.clear();
        type.clear();
        rating.clear();
        comments.clear();
    }

    public void appendList(double lati, double longi, String typ,
                           int rati, String comm)
    {
        int idxlati = latitude.indexOf(lati);
        (comments.get(idxlati)).add(comm);
        Double idxrati = rating.get(idxlati);
        rating.remove(idxlati);
        rating.add(idxlati, (idxrati + (double) rati) / 2);
    }

}
