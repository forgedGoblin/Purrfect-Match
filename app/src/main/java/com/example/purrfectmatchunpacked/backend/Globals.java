package com.example.purrfectmatchunpacked.backend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.storage.FirebaseStorage;

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

    public static void showMsg (String msg){
        Toast.makeText(null, msg, Toast.LENGTH_LONG).show();
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

    public void delayWhileNotEndLoad(){
        while (!safeLoad) {}
    }




}
