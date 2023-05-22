package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.purrfectmatchunpacked.backend.Cat;
import com.example.purrfectmatchunpacked.backend.Globals;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    TextView user;
    FirebaseAuth auth;
    FirebaseFirestore db;
    DocumentReference userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = findViewById(R.id.tvName);
        user.setText(Globals.currentUser.fname);
        TextView address = findViewById(R.id.tvAddress);
        address.setText(Globals.getCity(this));

        ImageView menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                                return true;
                            case R.id.wallet:
                                Toast.makeText(HomeActivity.this, "Wallet has been pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.settings:
                                Toast.makeText(HomeActivity.this, "Settings has been pressed", Toast.LENGTH_SHORT).show();
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

        ImageButton adoptButton = findViewById(R.id.adopt_button);
        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.load(getThis());
                getCats();
            }
        });

        ImageButton donateButton = findViewById(R.id.donate_button);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DonateActivity.class);
                startActivity(intent);
            }
        });

        ImageButton shopButton = findViewById(R.id.shop_button);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        ImageButton announceButton = findViewById(R.id.announce_button);
        announceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AnnouncementsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton trackAdoptButton = findViewById(R.id.traAdopt_button);
        trackAdoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrackAdoptionActivity.class);
                startActivity(intent);
            }
        });

        ImageButton trackOrdersButton = findViewById(R.id.traOrder_button);
        trackOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrackOrdersActivity.class);
                startActivity(intent);
            }
        });

    }

    AppCompatActivity getThis () {return this;}


    ArrayList<Cat> getCats () {

        ArrayList<Cat> cats = new ArrayList<>();
        Globals.db.collection("cats").whereEqualTo("isAdopted", false)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (var shard : task.getResult()) {
                            Cat cat = new Cat();
                            cat.ID = shard.getId();
                            cat.name = (String)shard.get("name");
                            cat.extension = (String)shard.get("extension");
                            cat.sex = (String)shard.get("sex");
                            cat.age = (String)shard.get("age");
                            cat.organization = (String)shard.get("org");

                            cats.add(cat);
                         //   Globals.showMsg(this, "Getting cat!");
                        } Globals.endLoad();
                        Intent intent = new Intent(HomeActivity.this, SelectCatActivity.class);
                        intent.putExtra("cats", cats);
                        Globals.startActivityOnFinish(getThis(), intent);
                    } else {
                        Globals.showMsg(this,"There was an error when retrieving the cats. Please try again on another time.");
                    }
                });
        return cats;
    }
}
