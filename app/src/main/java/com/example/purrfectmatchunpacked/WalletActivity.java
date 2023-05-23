package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {

    LinearLayout buttonContainer;
    Button addButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        buttonContainer = findViewById(R.id.buttonContainer);
        addButton = findViewById(R.id.addButton);

        List<String> items = new ArrayList<>();
        items.add("Debit Card");
        items.add("Online Wallet");
        items.add("Cash on Delivery");
        items.add("Barter");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setText("Credit Card");
        autoCompleteTextView.setAdapter(adapter);
    }

    private void addButtonToContainer() {
        Button newButton = new Button(this);
        newButton.setText("New Button");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        buttonContainer.addView(newButton, layoutParams);
    }
}
