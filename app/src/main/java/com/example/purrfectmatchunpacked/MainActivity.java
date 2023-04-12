package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                Toast.makeText(MainActivity.this, "Account has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.wallet:
                                Toast.makeText(MainActivity.this, "Wallet has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.settings:
                                Toast.makeText(MainActivity.this, "Settings has been pressed", Toast.LENGTH_SHORT).show();
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

        Button adoptButton = findViewById(R.id.adopt_button);
        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdoptActivity.class);
                startActivity(intent);
            }
        });

        Button donateButton = findViewById(R.id.donate_button);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DonateActivity.class);
                startActivity(intent);
            }
        });

    }
}
