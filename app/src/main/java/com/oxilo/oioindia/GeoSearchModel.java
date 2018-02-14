package com.oxilo.oioindia;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;


import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GeoSearchModel {


    public static String addressByLocation(double latitude,double longitude,Context context) {
        String address = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            if (geocoder.getFromLocation(latitude, longitude, 1) == null || geocoder.getFromLocation(latitude, longitude, 1).size() ==0)
                return address;
            Address geoAddress = geocoder.getFromLocation(latitude, longitude, 1).get(0);
            address = geoAddress.getLocality() != null ? geoAddress.getLocality() : geoAddress.getAdminArea();
            address = address == null ? geoAddress.getCountryName() : address + ", " + geoAddress.getCountryName();
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                getStringFromAddress(latitude,longitude);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return address;
        }
    }

    public static String getCityInfo(double latitude, double longitude,Context context){

        String address = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            if (geocoder.getFromLocation(latitude, longitude, 1) == null || geocoder.getFromLocation(latitude, longitude, 1).size() ==0)
                return address;
            Address geoAddress = geocoder.getFromLocation(latitude, longitude, 1).get(0);
            address = geoAddress.getLocality();
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return address;
        }

    }


    public static List<Address> getStringFromAddress(double lat, double lng)
            throws ClientProtocolException, IOException, JSONException {

        LatLng latLng = new LatLng(lat,lng);

        String address = String
                .format(Locale.ENGLISH,                                 "http://maps.googleapis.com/maps/api/geocode/json?latlng="+latLng+"&sensor=true&language="
                        + Locale.getDefault().getCountry(), lat, lng);
        HttpGet httpGet = new HttpGet(address);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        List<Address> retList = null;

        response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream stream = entity.getContent();
        int b;
        while ((b = stream.read()) != -1) {
            stringBuilder.append((char) b);
        }

        JSONObject jsonObject = new JSONObject(stringBuilder.toString());

        retList = new ArrayList<Address>();

        if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String indiStr = result.getString("formatted_address");
                Address addr = new Address(Locale.getDefault());
                addr.setAddressLine(0, indiStr);
                retList.add(addr);
            }
        }

        return retList;
    }

    public static List<Address> getStringFromCity(double lat, double lng)
            throws ClientProtocolException, IOException, JSONException {

        String address = "http://maps.googleapis.com/maps/api/geocode/json?latlng=12&sensor=true";
        HttpGet httpGet = new HttpGet(address);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        List<Address> retList = null;

        response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream stream = entity.getContent();
        int b;
        while ((b = stream.read()) != -1) {
            stringBuilder.append((char) b);
        }

        JSONObject jsonObject = new JSONObject(stringBuilder.toString());

        retList = new ArrayList<Address>();

        if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String indiStr = result.getString("formatted_address");
                Address addr = new Address(Locale.getDefault());
                addr.setAddressLine(0, indiStr);
                retList.add(addr);
            }
        }

        return retList;
    }



    public static String getAdminArea(double latitude, double longitude,Context context){

        String address = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            if (geocoder.getFromLocation(latitude, longitude, 1) == null || geocoder.getFromLocation(latitude, longitude, 1).size() ==0)
                return address;
            Address geoAddress = geocoder.getFromLocation(latitude, longitude, 1).get(0);
            address = geoAddress.getAdminArea();
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return address;
        }

    }

    public static String fullAddressByLocation(double latitude, double longitude,Context context) {
        String address = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            if (geocoder.getFromLocation(latitude, longitude, 1) == null || geocoder.getFromLocation(latitude, longitude, 1).size() ==0)
                return address;
            Address geoAddress = geocoder.getFromLocation(latitude, longitude, 1).get(0);
            for (int i = 0; i < geoAddress.getMaxAddressLineIndex(); i++) {
                address += geoAddress.getAddressLine(i) + " ";
            }
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return address;
        }
    }
}
