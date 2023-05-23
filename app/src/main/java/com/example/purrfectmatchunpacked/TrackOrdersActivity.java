package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import com.example.purrfectmatchunpacked.backend.Globals;
import com.example.purrfectmatchunpacked.backend.Orders;

import java.util.ArrayList;

public class TrackOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);
        ImageView menuButton = findViewById(R.id.menuButton);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);
        TextView address = findViewById(R.id.tvAddress);
        address.setText(Globals.getCity(this));
        Button addButton = findViewById(R.id.addButton);
        addButton.setVisibility(View.INVISIBLE);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(TrackOrdersActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                Toast.makeText(TrackOrdersActivity.this, "Account has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.wallet:
                                Toast.makeText(TrackOrdersActivity.this, "Wallet has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.settings:
                                Toast.makeText(TrackOrdersActivity.this, "Settings has been pressed", Toast.LENGTH_SHORT).show();
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
        Bundle extra = getIntent().getExtras();
        var orders = (ArrayList<Orders>)extra.get("orders");
        for (var order : orders){
            Button dynamicButton = new Button(this);
            dynamicButton.setText(order.type);
            dynamicButton.setOnClickListener( listen -> {
                new AlertDialog.Builder(this)
                        .setTitle(order.type)
                        .setMessage("Ordered by: " + order.fname + " " + order.lname + "\n" +
                                "Ordered on: " + order.time + "\n" +
                                "Order status: " + order.status).show();
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.width = 1000;  // Set the desired width in pixels
            layoutParams.height = 200; // Set the desired height in pixels
            dynamicButton.setLayoutParams(layoutParams);

            // Add the button to the container layout
            buttonContainer.addView(dynamicButton);
        } Globals.endLoad();
    }
}