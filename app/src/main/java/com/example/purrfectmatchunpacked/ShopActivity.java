package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purrfectmatchunpacked.backend.Globals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity {

    ImageButton catFood;
    ImageButton catResources;
    public AppCompatActivity getThis(){return this;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);
        TextView address = findViewById(R.id.tvAddress);
        address.setText(Globals.getCity(this));

        ImageView menuButton = findViewById(R.id.menuButton);
        catFood = findViewById(R.id.imageButton9);
        catResources = findViewById(R.id.imageButton11);

        catFood.setOnClickListener(l -> {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // generates ID from FireBase
                            String id = Globals.db.collection("orders").document().getId();
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("type", "cat food");
                            data.put("user", Globals.currentUser.email);
                            data.put("time", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                            data.put("fname", Globals.currentUser.fname);
                            data.put("lname", Globals.currentUser.lname);
                            data.put("status", "pending store confirmation");
                            // adds orders to the database
                            Globals.db.collection("orders").document(id).set(data).addOnSuccessListener(suc -> {
                                Toast.makeText(getThis(), "Cat food purchased!\nPlease check your orders.", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(er -> {
                                Toast.makeText(getThis(), "Cat food purchase failed. Server issue.", Toast.LENGTH_SHORT).show();
                            });
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                        case  DialogInterface.BUTTON_NEUTRAL:
                            Toast.makeText(getThis(), "Cat food purchase cancelled.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            catResources.setOnClickListener(listen -> {
                DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String id = Globals.db.collection("orders").document().getId();
                                HashMap<String, Object> data = new HashMap<>();
                                data.put("type", "cat resources");
                                data.put("user", Globals.currentUser.email);
                                data.put("time", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                                data.put("fname", Globals.currentUser.fname);
                                data.put("lname", Globals.currentUser.lname);
                                data.put("status", "pending store confirmation");
                                // adds orders to the database
                                Globals.db.collection("orders").document(id).set(data).addOnSuccessListener(suc -> {
                                    Toast.makeText(getThis(), "Cat food purchased!\nPlease check your orders.", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(getThis(), "Cat food purchase failed. Server issue.", Toast.LENGTH_SHORT).show();
                                });
                                Toast.makeText(getThis(), "Cat resources purchased!\nPlease check your orders.", Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                            case  DialogInterface.BUTTON_NEUTRAL:
                                Toast.makeText(getThis(), "Cat resources purchase cancelled.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Confirm cat resources purchase?").setPositiveButton("Yes", dialogClickListener1)
                        .setNegativeButton("No", dialogClickListener1).show();
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Confirm cat food purchase?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        });






        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ShopActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                startActivity(new Intent(ShopActivity.this, AccountActivity.class));
                                return true;
                            case R.id.wallet:
                                startActivity(new Intent(ShopActivity.this, WalletActivity.class));
                                return true;
                            case R.id.settings:
                                startActivity(new Intent(ShopActivity.this, SettingsActivity.class));
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