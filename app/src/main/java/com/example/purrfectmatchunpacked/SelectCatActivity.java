package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.purrfectmatchunpacked.backend.Cat;
import com.example.purrfectmatchunpacked.backend.Globals;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectCatActivity extends AppCompatActivity {
    private String currentCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cat);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);
        var nameNow = Globals.currentUser.fname;

        /* Dynamically add buttons */
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        Button addButton = findViewById(R.id.addButton);
        addButton.setVisibility(View.INVISIBLE);
        TextView address = findViewById(R.id.tvAddress);
        address.setText(Globals.getCity(this));

        Bundle extra = getIntent().getExtras();
        var cats = (ArrayList<Cat>)extra.get("cats");
                // Create a new button instance
                for (var cat : cats) {
                    Button dynamicButton = new Button(SelectCatActivity.this);
                    dynamicButton.setText(cat.name);
                    dynamicButton.setOnClickListener(l -> {
                        Globals.load(getThis());
                        Intent intent = new Intent(SelectCatActivity.this, AdoptActivity.class);
                        intent.putExtra("cat", cat);
                        var reference = Globals.storage.getReference();
                        var path = reference.child("cats/"+cat.ID);
                        path.getDownloadUrl().addOnSuccessListener(URI -> {
                            Globals.currentImage = Picasso.get().load(URI);
                            startActivity(intent);
                        }).addOnFailureListener(failure -> {
                            Globals.endLoad();
                            Globals.showMsg(this, "A problem occurred when downloading " + cat.name + "'s data. Please try again later.");
                        });
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

    AppCompatActivity getThis () {return this;}




}