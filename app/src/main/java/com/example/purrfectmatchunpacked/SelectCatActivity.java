package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.purrfectmatchunpacked.backend.Cat;

import java.util.ArrayList;

public class SelectCatActivity extends AppCompatActivity {
    private String currentCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cat);

        /* Dynamically add buttons */
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        Button addButton = findViewById(R.id.addButton);
        addButton.setVisibility(View.INVISIBLE);
        Bundle extra = getIntent().getExtras();
        var cats = (ArrayList<Cat>)extra.get("cats");
                // Create a new button instance
                for (var cat : cats) {
                    Button dynamicButton = new Button(SelectCatActivity.this);
                    dynamicButton.setText(cat.name);
                    dynamicButton.setOnClickListener(l -> {
                        Intent intent = new Intent(SelectCatActivity.this, AdoptActivity.class);
                        intent.putExtra("cat", cat);
                        startActivity(intent);
                    });

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
                }






    }




}