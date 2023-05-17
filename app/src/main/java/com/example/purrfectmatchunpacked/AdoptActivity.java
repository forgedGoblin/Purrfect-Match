package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purrfectmatchunpacked.backend.Cat;
import com.example.purrfectmatchunpacked.backend.Globals;
import com.google.firebase.firestore.DocumentReference;

public class AdoptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);
        ImageView cleoView = findViewById(R.id.ivCleo);
        DocumentReference user = Globals.db.collection("users").document("testadmin@gmail.com");
        user.get().addOnCompleteListener( v -> {
            var userDocument = v.getResult();
            var x = (String)userDocument.get("email");
            System.out.println(x);
        }).addOnFailureListener( x -> {
            System.out.println("Error!");
        });


        var array = Cat.getCats();
        //cleoView.setImageBitmap(Cat.getCats().get(1).getBitmap());
   //     var cat = array.get(0);
        ImageView menuButton = findViewById(R.id.menuButton);


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
    }
}