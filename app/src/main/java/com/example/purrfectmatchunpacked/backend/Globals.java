package com.example.purrfectmatchunpacked.backend;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.storage.FirebaseStorage;

public class Globals {
    public static User currentUser = null;

    public static void initUser (String email, String firstname, String lastname) {
        currentUser = new User(email, firstname, lastname);
    }

    public static FirebaseFirestore db;

    public static FirebaseStorage storage;
    public static boolean loadAwait = false;
    public static FirebaseAuth fireAuth = null;
    public static void initAuth(){
        fireAuth = FirebaseAuth.getInstance();
    }

    public static void logout(){
        fireAuth.signOut();
    }





}
