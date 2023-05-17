package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SelectCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cat);

        /* Dynamically add buttons */
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            private int catCount = 1;
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Create a new button instance
                Button dynamicButton = new Button(SelectCatActivity.this);
                dynamicButton.setText("Cat #" + catCount);

                // Set width and height using LayoutParams
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.width = 1000;  // Set the desired width in pixels
                layoutParams.height = 200; // Set the desired height in pixels
                dynamicButton.setLayoutParams(layoutParams);

                // Add the button to the container layout
                buttonContainer.addView(dynamicButton);

                // Increment the button count
                catCount++;
            }
        });





    }
}