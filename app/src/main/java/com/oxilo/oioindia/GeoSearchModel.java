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
                address =  getStringFromAddress(latitude,longitude);
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

            try {
                address =  getStringFromCity(latitude,longitude);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return address;
        }

    }


    public static String getStringFromAddress(double lat, double lng)
            throws ClientProtocolException, IOException, JSONException {

       // LatLng latLng = new LatLng(lat,lng);
//        System.out.println("### lat:-"+lat);
//        System.out.println("### lng:-"+lng);
        String address="";
        String get_all_address = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=true&key=AIzaSyAn6vvnWkrRnx96NtUitQ8Ml6KdBx65Y6U";
        HttpGet httpGet = new HttpGet(get_all_address);
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
        System.out.println("### address:-"+jsonObject.toString());
        retList = new ArrayList<Address>();
//
        if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
            JSONArray results = jsonObject.getJSONArray("results");
            if(results.length()>0){
                address=results.getJSONObject(0).getString("formatted_address");
            }

        }
        System.out.println("### address:-"+address);


        return address;
    }

    public static String getStringFromCity(double lat, double lng)
            throws ClientProtocolException, IOException, JSONException {

        // LatLng latLng = new LatLng(lat,lng);
//        System.out.println("### lat:-"+lat);
//        System.out.println("### lng:-"+lng);
        String address="";
        String get_all_address = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=true&key=AIzaSyAn6vvnWkrRnx96NtUitQ8Ml6KdBx65Y6U";
        HttpGet httpGet = new HttpGet(get_all_address);
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
        System.out.println("### address:-"+jsonObject.toString());
        retList = new ArrayList<Address>();
//
        if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
            JSONArray results = jsonObject.getJSONArray("results");
            if(results.length()>0){
                JSONArray jsonArray =results.getJSONObject(0).getJSONArray("address_components");
                boolean flag=false;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    for(int j=0;j<jsonObject1.getJSONArray("types").length();j++){
                        if(jsonObject1.getJSONArray("types").getString(j).equals("locality")){
                            flag=true;
                            break;
                        }
                    }
                    if(flag){
                        address=jsonObject1.getString("long_name");
                        break;
                    }
                }
//                address=results.getJSONObject(0).getString("formatted_address");
            }

        }
        System.out.println("### locality:-"+address);


        return address;
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
