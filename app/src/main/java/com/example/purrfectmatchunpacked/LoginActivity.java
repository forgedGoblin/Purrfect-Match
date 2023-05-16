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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email;
    EditText password;
    TextView warning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        warning = findViewById(R.id.warning);
        warning.setVisibility(View.INVISIBLE);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        ImageView registerWave = findViewById(R.id.greenWave);
        Resources res = this.getResources();
        int newColor = res.getColor(R.color.secondary);
        registerWave.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivity.this, "Please fill-in all the fields", Toast.LENGTH_LONG).show();
                return;
            }
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                warning.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
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




        });

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}