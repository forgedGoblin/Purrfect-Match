package com.example.purrfectmatchunpacked;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.SubMenu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.purrfectmatchunpacked.backend.Globals;

public class DonateActivity extends AppCompatActivity {

    private static final String[] buttonUrls = {
            "https://www.facebook.com/catsofcebuitpark/",
            "https://www.facebook.com/HappyTailsAnimalWelfare",
            "https://www.facebook.com/SavingStraysCebu",
            "https://www.facebook.com/catsofdongilargao"
    };

    private static final int[] buttonIds = {
            R.id.btnITPark,
            R.id.btnHappy,
            R.id.btnSavingStrays,
            R.id.btnDonGil
    };

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        TextView address = findViewById(R.id.tvAddress);
        address.setText(Globals.getCity(this));

        ImageView menuButton = findViewById(R.id.menuButton);
        TextView name = findViewById(R.id.tvName);
        name.setText(Globals.currentUser.fname);

        menuButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(DonateActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.account:
                        startActivity(new Intent(DonateActivity.this, AccountActivity.class));
                        return true;
                    case R.id.wallet:
                        startActivity(new Intent(DonateActivity.this, WalletActivity.class));
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(DonateActivity.this, SettingsActivity.class));
                        return true;
                    default:
                        return false;
                }
            });

            if (popupMenu.getMenu().getItem(0).hasSubMenu()) {
                SubMenu subMenu = popupMenu.getMenu().getItem(0).getSubMenu();
                if (subMenu != null && subMenu.getItem() != null) {
                    subMenu.getItem().getIcon().setColorFilter(Color.parseColor("#F0FFFF"), PorterDuff.Mode.SRC_ATOP);
                }
            }
            popupMenu.show();
        });

        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton button = findViewById(buttonIds[i]);
            final String url = buttonUrls[i];
            button.setOnClickListener(v -> openBrowser(url));
        }
    }

    private void openBrowser(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Verify if there is a web browser app available to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}