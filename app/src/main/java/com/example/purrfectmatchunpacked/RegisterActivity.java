package com.example.purrfectmatchunpacked;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth authenticator;
    Button register;
    EditText email;
    EditText password;
    BeginSignInRequest signInRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authenticator = FirebaseAuth.getInstance();
        register = findViewById(R.id.register_button);
        email = findViewById(R.id.emailEMail);
        password = findViewById(R.id.passPass);


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
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
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
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
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
            authenticator.signOut();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            // handle valid here
        }
    }

}