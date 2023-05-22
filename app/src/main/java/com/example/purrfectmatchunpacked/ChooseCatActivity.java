package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purrfectmatchunpacked.backend.Cat;
import com.example.purrfectmatchunpacked.backend.Globals;

import java.util.ArrayList;

public class ChooseCatActivity extends AppCompatActivity {
    private String currentCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cat);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);
        ImageView menuButton = findViewById(R.id.menuButton);

        /* Dynamically add buttons */
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        Button addButton = findViewById(R.id.addButton);
        addButton.setVisibility(View.INVISIBLE);
        Bundle extra = getIntent().getExtras();
        var cats = (ArrayList<Cat>)extra.get("cats");
                // Create a new button instance
                for (var cat : cats) {
                    Button dynamicButton = new Button(ChooseCatActivity.this);
                    dynamicButton.setText(cat.name);
                    dynamicButton.setOnClickListener(l -> {
                        Intent intent = new Intent(ChooseCatActivity.this, AdoptActivity.class);
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

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ChooseCatActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                Toast.makeText(ChooseCatActivity.this, "Account has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.wallet:
                                Toast.makeText(ChooseCatActivity.this, "Wallet has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.settings:
                                Toast.makeText(ChooseCatActivity.this, "Settings has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                if (popupMenu.getMenu().getItem(0).hasSubMenu()) {
                    SubMenu subMenu = popupMenu.getMenu().getItem(0).getSubMenu();
                    if (subMenu != null && subMenu.getItem() != null) {
                        subMenu.getItem().getIcon().setColorFilter(Color.parseColor("#F0FFFF"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
                popupMenu.show();
            }
        });

    }
}