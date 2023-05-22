package com.example.purrfectmatchunpacked.backend;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import com.squareup.picasso.RequestCreator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.purrfectmatchunpacked.StartActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

    /*================================
    ================================== */

public class Globals {



    /*================================
    Holds the current user instance.
    ================================== */
    public static User currentUser = null;

    /*================================
    Initializes the current user instance.
    ================================== */
    public static void initUser (String email, String firstname, String lastname) {
        currentUser = new User(email, firstname, lastname);
    }

    /*================================
    Holds the static FirebaseDB object.
    ================================== */
    public static FirebaseFirestore db;

    /*================================
    Holds the static FirebaseStorage
    object.
    ================================== */

    public static FirebaseStorage storage;


    /*================================
    Holds the static Firebase Auth
    instance.
    ================================== */
    public static FirebaseAuth fireAuth = null;

    /*================================
    Initializes the Auth.
    ================================== */

    public static void initAuth(){
        fireAuth = FirebaseAuth.getInstance();
    }


    /*================================
    Signs out the current firebase
    instance.
    ================================== */

    public static void logout(){
        fireAuth.signOut();
    }


    /*================================
    For debugging: redirects the control
    flow based on debugging status.
    ================================== */
    public static boolean testMode = false;


    /*================================
    The debugging credentials.
    ================================== */
    public static String testEmail ="testadmin@gmail.com";
    public static String testPassword = "testadmin";

    public static void showMsg (AppCompatActivity instance, String msg){
        Toast.makeText(instance, msg, Toast.LENGTH_LONG).show();
    }



    /*================================
    These are progress dialogs meant to
    halt execution whenever we are
    waiting for FireBase to retrieve the
    objects.
    ================================== */
    private static ProgressDialog dlog = null;


    /*================================
    Makes a loading bar and will continue
    until a call to endLoad() functions.
    ================================== */
    public static ProgressDialog load (Context view) {
        dlog = new ProgressDialog(view);
        dlog.setTitle("Fetching the cats...");
        dlog.setMessage("Please wait while we load your purrfect match!");
        dlog.setCancelable(false);
        dlog.show();
        safeLoad = false;
        return dlog;
    }

    public static void endLoad (){
        dlog.hide();
        safeLoad = true;
    }


    /*================================
    While loading, safeLoad is set to
    false. Switches to true upon
    endLoad(). Makes sure that it
    is safe to execute a task because
    all is loaded.
    ================================== */
    private static boolean safeLoad = true;


    /*================================
    Given an activity, launches an
    activity upon endLoad().
    ================================== */
    public static void startActivityOnFinish(Activity prev, Intent activity){
        while (!safeLoad) {}
        prev.startActivity(activity);
    }

    /*================================
    Introduces a delay while loading.
    ================================== */
    public static void delayWhileLoading(){
        while (!safeLoad) {}
    }


    /*================================
    Calculates the file extension
    given a path.
    ================================== */
    public static String getExtension(String path){
        StringBuilder builder = new StringBuilder();
        boolean mode = false;
        for (int i=0; i<path.length(); i++){
            if (path.charAt(i) == '/') {
                mode = true;
                continue;
            }
            if (mode){
                builder.append(path.charAt(i));
            }
        } return builder.toString();
    }

    /*================================
    The cat image to load.
    ================================== */
    public static RequestCreator currentImage;

    public static LocationManager locationManager;

    public static void initLocationManager(AppCompatActivity instance){
        locationManager = (LocationManager)instance.getSystemService(Context.LOCATION_SERVICE);
    }

    public static Location getLocation() {
        Criteria criteria = new Criteria();
        var provider = locationManager.getBestProvider(criteria, false);
        return locationManager.getLastKnownLocation(provider);
    }

    public static String getCity (Context context) {
        var location = getLocation();
        double lat = (int) location.getLatitude();
        double lon = (int) location.getLongitude();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (Exception ignored){}
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        var splits = cityName.split(",");
        return splits[0] + "\n" + splits[1] + "\n" + splits[2];
    }
}
