package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity {

    private Button btnAcc2Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btnAcc2Home = findViewById(R.id.btnAcc2Home);
        btnAcc2Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomeActivity
                Intent intent = new Intent(AccountActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}