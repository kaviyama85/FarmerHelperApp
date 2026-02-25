package com.example.farmerhelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner cropSpinner, soilTypeSpinner, seasonSpinner;
    private Button callButton, smsButton, emailButton, getGuidanceButton;
    private TextView farmingTipsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cropSpinner = findViewById(R.id.crop_spinner);
        soilTypeSpinner = findViewById(R.id.soil_type_spinner);
        seasonSpinner = findViewById(R.id.season_spinner);
        farmingTipsTextView = findViewById(R.id.farming_tips_textview);

        callButton = findViewById(R.id.call_button);
        smsButton = findViewById(R.id.sms_button);
        emailButton = findViewById(R.id.email_button);
        getGuidanceButton = findViewById(R.id.get_guidance_button);

        // Populate Spinners
        ArrayAdapter<CharSequence> cropAdapter = ArrayAdapter.createFromResource(this,
                R.array.crop_array, android.R.layout.simple_spinner_item);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(cropAdapter);

        ArrayAdapter<CharSequence> soilTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.soil_type_array, android.R.layout.simple_spinner_item);
        soilTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soilTypeSpinner.setAdapter(soilTypeAdapter);

        ArrayAdapter<CharSequence> seasonAdapter = ArrayAdapter.createFromResource(this,
                R.array.season_array, android.R.layout.simple_spinner_item);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(seasonAdapter);

        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFarmingTips();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        cropSpinner.setOnItemSelectedListener(spinnerListener);
        soilTypeSpinner.setOnItemSelectedListener(spinnerListener);
        seasonSpinner.setOnItemSelectedListener(spinnerListener);

        getGuidanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CropGuidanceActivity.class);
                intent.putExtra("CROP", cropSpinner.getSelectedItem().toString());
                intent.putExtra("SOIL_TYPE", soilTypeSpinner.getSelectedItem().toString());
                intent.putExtra("SEASON", seasonSpinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("call");
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("sms");
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("email");
            }
        });
        updateFarmingTips();
    }

    private void updateFarmingTips() {
        String crop = cropSpinner.getSelectedItem().toString();
        String soilType = soilTypeSpinner.getSelectedItem().toString();
        String season = seasonSpinner.getSelectedItem().toString();

        if (crop.equals("Rice") && soilType.equals("Loamy") && season.equals("Monsoon")) {
            farmingTipsTextView.setText("• Use high-yielding rice varieties like IR 64 for better yield.\n• Ensure proper water management and maintain field moisture.\n• Apply balanced fertilizer containing nitrogen, phosphorus, and potassium as per soil test recommendations.");
        } else {
            String tips = "Showing tips for Crop: " + crop + ", Soil Type: " + soilType + ", Season: " + season;
            farmingTipsTextView.setText(tips);
        }
    }

    private void showConfirmationDialog(final String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm " + capitalize(action));
        builder.setMessage("Do you want to " + action + " the agricultural officer?");
        builder.setPositiveButton(capitalize(action), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performAction(action);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void performAction(String action) {
        String phoneNumber = "1234567890"; // Replace with actual phone number
        String emailAddress = "expert@example.com"; // Replace with actual email

        switch (action) {
            case "call":
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
                break;
            case "sms":
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:" + phoneNumber));
                startActivity(smsIntent);
                break;
            case "email":
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + emailAddress));
                startActivity(emailIntent);
                break;
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}