package com.example.purrfectmatchunpacked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purrfectmatchunpacked.backend.Globals;
import com.example.purrfectmatchunpacked.backend.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    TextView warning;

    AppCompatActivity getThis (){
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        warning = findViewById(R.id.warning);
        warning.setVisibility(View.INVISIBLE);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        Globals.testMode = true;
        ImageView registerWave = findViewById(R.id.greenWave);
        Resources res = this.getResources();
        int newColor = res.getColor(R.color.secondary);
        registerWave.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        Globals.fireAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.login_button);


        if (Globals.testMode) {
            unitTest();
            return;
        }
        loginButton.setOnClickListener(view -> {
            Globals.load(this);
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
            {
                Globals.endLoad();
                Toast.makeText(LoginActivity.this, "Please fill-in all the fields", Toast.LENGTH_LONG).show();
                return;
            }
            initUser();
            Globals.fireAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener( task -> {
                        Globals.storage = FirebaseStorage.getInstance();
                        Globals.initAuth();
                        Globals.startActivityOnFinish(getThis(), new Intent(LoginActivity.this, HomeActivity.class));
                    }).addOnFailureListener(exception -> {
                        Globals.endLoad();
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
        });


        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            Globals.startActivityOnFinish(this, intent);
        });
    }

    public void initUser(){
        Globals.db = FirebaseFirestore.getInstance();
        DocumentReference user = null;
        if (!Globals.testMode)
        user = Globals.db.collection("users").document(email.getText().toString());
        else user = Globals.db.collection("users").document(Globals.testEmail);


        user.get().addOnSuccessListener(userDocument -> {
            Globals.initUser((String)userDocument.getId(),(String)userDocument.get("fname"), (String)userDocument.get("lname"));
            warning.setVisibility(View.INVISIBLE);
            Globals.endLoad();
            Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_LONG).show();
        }).addOnFailureListener( x -> {
            Globals.endLoad();
            warning.setText("Invalid error instance with the server.");
            warning.setVisibility(View.VISIBLE);
        }).addOnCanceledListener(() -> {
            warning.setText("This process was cancelled.");
            Globals.endLoad();
            warning.setVisibility(View.VISIBLE);
        });

    }


    public void unitTest(){
        if (!Globals.testMode) return;
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> {
            Globals.load(this);
            initUser();
        Globals.fireAuth.signInWithEmailAndPassword(Globals.testEmail, Globals.testPassword)
                .addOnCompleteListener( v -> {
                        Globals.storage = FirebaseStorage.getInstance();
                        Globals.fireAuth = FirebaseAuth.getInstance();
                        Globals.startActivityOnFinish(this, new Intent(LoginActivity.this, HomeActivity.class));
                }).addOnFailureListener(exception -> {
                    Globals.endLoad();
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

        });

    }

}