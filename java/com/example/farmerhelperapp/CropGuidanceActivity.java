package com.example.farmerhelperapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CropGuidanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_guidance);

        TextView guidanceTextView = findViewById(R.id.guidance_textview);

        String crop = getIntent().getStringExtra("CROP");
        String soilType = getIntent().getStringExtra("SOIL_TYPE");
        String season = getIntent().getStringExtra("SEASON");

        String guidance = "Crop: " + crop + "\nSoil Type: " + soilType + "\nSeason: " + season + "\n\nDetailed guidance will be displayed here.";

        guidanceTextView.setText(guidance);
    }
}