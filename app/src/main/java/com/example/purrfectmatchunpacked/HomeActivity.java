package com.example.purrfectmatchunpacked;

import androidx.annotation.NonNull;
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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purrfectmatchunpacked.backend.Connector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.sql.SQLException;


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
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userInfo = db.collection("users").document(auth.getCurrentUser().getEmail());
        userInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    user.setText((String)task.getResult().get("fname"));
                }
            }
        });


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

        Button adoptButton = findViewById(R.id.adopt_button);
        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AdoptActivity.class);
                startActivity(intent);
            }
        });

        Button donateButton = findViewById(R.id.donate_button);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DonateActivity.class);
                startActivity(intent);
            }
        });

        Button shopButton = findViewById(R.id.shop_button);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        Button msgButton = findViewById(R.id.messages_button);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
        });

        Button trackAdoptButton = findViewById(R.id.traAdopt_button);
        trackAdoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrackAdoptionActivity.class);
                startActivity(intent);
            }
        });

        Button trackOrdersButton = findViewById(R.id.traOrder_button);
        trackOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrackOrdersActivity.class);
                startActivity(intent);
            }
        });

    }
}
