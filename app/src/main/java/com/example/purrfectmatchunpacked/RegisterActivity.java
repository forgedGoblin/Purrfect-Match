package com.example.purrfectmatchunpacked;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    com.google.firebase.auth.FirebaseAuth authenticator;
    FirebaseFirestore db;

    Button register;
    EditText email;
    EditText password;
    EditText fname;
    EditText lname;
    TextView warning;

    BeginSignInRequest signInRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authenticator = FirebaseAuth.getInstance();
        register = findViewById(R.id.register_button);
        warning = findViewById(R.id.warning2);
        warning.setVisibility(View.INVISIBLE);
        email = findViewById(R.id.emailEMail);
        password = findViewById(R.id.passPass);
        fname = findViewById(R.id.fName);
        lname = findViewById(R.id.lName);
        db = FirebaseFirestore.getInstance();
        fname.setHint("First Name");
        lname.setHint("Last Name");





        // Check if user is already logged in

        if (authenticator.getCurrentUser() != null)
        {
            // reloads the user back to the home screen
          //  startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        register.setOnClickListener(view ->
        {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                || fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please fill-in all the fields.", Toast.LENGTH_LONG).show();
                return;
            }
            createNewUser(email.getText().toString(), password.getText().toString());
        });
    }


    void createNewUser(String email, String password)
    {
        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = authenticator.getCurrentUser();
                            updateUI(email);
                        }
                        else
                        {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            updateUI(null);
                        }

                    }
                }).addOnFailureListener(exception -> {
                    String messageAct = "";
                    if (exception instanceof FirebaseAuthWeakPasswordException) {
                        messageAct = "Please use a stronger password.";
                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                        messageAct = "This user already exists. PLease login.";
                    }  else if (exception instanceof FirebaseNetworkException) {
                        messageAct = "This user is not connected to the internet. Please try again.";
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        messageAct = "The format of the credentials is incorrect. Please try again.";
                    }
                    else if (exception instanceof Exception) {
                        messageAct = "General exception occurred with error code: " + exception.toString();
                    }


                    warning.setText(messageAct);
                    warning.setVisibility(View.VISIBLE);
                });

        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();

    }

    private void updateUI(String email) {
        // load this activity
        if (email == null)
        {
            // handle invalid here
        }
        else
        {
            CollectionReference users = db.collection("users");
            DocumentReference document = users.document(email);
            Map<String, String> data = new HashMap<>();
            data.put("fname", fname.getText().toString());
            data.put("lname", lname.getText().toString());
            document.set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this, "Success! Saved credentials.", Toast.LENGTH_LONG)
                                            .show();
                                    authenticator.signOut();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Oh no! An error occurred.", Toast.LENGTH_LONG);
                            new AlertDialog.Builder(RegisterActivity.this).setMessage(e.toString()).setTitle("Error caused by:").show();
                        }
                    })
            ;
        }
    }

}