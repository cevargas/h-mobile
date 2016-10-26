package com.tn3.mobile.hermes.utils;
/*
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
*/
public class MapLocation {
    public String name;
    //public LatLng center;

    @SuppressWarnings("unused")
    public MapLocation() {}

    public MapLocation(String name, double lat, double lng) {
        this.name = name;
        //this.center = new LatLng(lat, lng);
    }

}