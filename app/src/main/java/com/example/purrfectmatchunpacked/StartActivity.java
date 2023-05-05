package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = findViewById(R.id.login_button);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}