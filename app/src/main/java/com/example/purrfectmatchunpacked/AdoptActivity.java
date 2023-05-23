package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purrfectmatchunpacked.backend.Cat;
import com.example.purrfectmatchunpacked.backend.Globals;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AdoptActivity extends AppCompatActivity {
    Button adoptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);
        adoptButton = findViewById(R.id.button);
        TextView address = findViewById(R.id.tvAddress);
        address.setText(Globals.getCity(this));
        TextView catName = findViewById(R.id.tvCleo);
        TextView orgName = findViewById(R.id.tvCfITP);
        TextView gender = findViewById(R.id.tvFemale);
        TextView age = findViewById(R.id.tv2);
        ImageView cleoView = findViewById(R.id.ivCleo);
        Globals.currentImage.into(cleoView);
        Globals.endLoad();
        Cat cat = (Cat)getIntent().getExtras().get("cat");
        ImageView menuButton = findViewById(R.id.menuButton);
        catName.setText(cat.name);
        orgName.setText(cat.organization);
        gender.setText(cat.sex);
        age.setText(cat.age);




        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(AdoptActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                Toast.makeText(AdoptActivity.this, "Account has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.wallet:
                                Toast.makeText(AdoptActivity.this, "Wallet has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.settings:
                                Toast.makeText(AdoptActivity.this, "Settings has been pressed", Toast.LENGTH_SHORT).show();
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

         adoptButton.setOnClickListener( l -> {
             DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     switch (which) {
                         case DialogInterface.BUTTON_POSITIVE:
                             // generates ID from FireBase
                             String id = Globals.db.collection("adopt_request").document().getId();
                             HashMap<String, Object> data = new HashMap<>();
                             data.put("email", Globals.currentUser.email);
                             data.put("time", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                             data.put("fname", Globals.currentUser.fname);
                             data.put("lname", Globals.currentUser.lname);
                             data.put("status", "pending organization confirmation");
                             data.put("organization", cat.organization);
                             data.put("cat", cat.name);
                             // adds orders to the database
                             Globals.db.collection("adopt_request").document(id).set(data).addOnSuccessListener(suc -> {
                                 Globals.db.collection("cats").document(cat.ID).update("isAdopted", true);
                                 Toast.makeText(getThis(), "Cat adoption request sent!\nPlease check your adoptions.", Toast.LENGTH_SHORT).show();
                             }).addOnFailureListener(er -> {
                                 Toast.makeText(getThis(), "Adoption request failed. Server issue.", Toast.LENGTH_SHORT).show();
                             });
                             break;
                         case DialogInterface.BUTTON_NEGATIVE:

                         case DialogInterface.BUTTON_NEUTRAL:
                             Toast.makeText(getThis(), "Cat adoption cancelled.", Toast.LENGTH_SHORT).show();
                             break;
                     }
                 }
             };

             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             builder.setMessage("Confirm cat adoption?").setPositiveButton("Yes", dialogClickListener)
                     .setNegativeButton("No", dialogClickListener).show();
         });

    }
        public AppCompatActivity getThis(){
            return this;
        }

}