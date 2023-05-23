package com.example.purrfectmatchunpacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {

    private final String[] advices = {
            "Follow your dreams.",
            "Stay positive.",
            "Never give up.",
            "Embrace change.",
            "Be kind to others.",
            "Learn from your mistakes.",
            "Believe in yourself.",
            "Stay curious and keep learning.",
            "Take risks and step out of your comfort zone.",
            "Stay focused on your goals.",
            "Practice gratitude.",
            "Value and prioritize your health.",
            "Cherish your relationships.",
            "Seek opportunities for personal growth.",
            "Embrace challenges as opportunities.",
            "Practice patience and persistence.",
            "Stay humble and open-minded.",
            "Celebrate small victories.",
            "Listen to your intuition.",
            "Find balance in all aspects of life.",
            "Be adaptable to changes.",
            "Practice self-care and self-love.",
            "Don't compare yourself to others.",
            "Stay organized and manage your time effectively.",
            "Remember that failure is a part of the journey.",
            "Live in the present moment."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView tvLogout = findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(v -> {
            // Handle logout button click
            Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity
        });

        TextView tvAdvices = findViewById(R.id.tvAdvices);
        tvAdvices.setOnClickListener(v -> showRandomAdvice());

    }
    private void showRandomAdvice() {
        Random random = new Random();
        int index = random.nextInt(advices.length);
        String randomAdvice = advices[index];
        Toast.makeText(this, randomAdvice, Toast.LENGTH_SHORT).show();
    }

}