package com.example.ggupt.htn2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    private Button placeBtn;
    private Button downloadBtn;
    private Button createBtn;

    private EditText pinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeUI();

        placeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ARPlacerActivity.class);
                startActivity(intent);
            }
        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pinText.getText().toString();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);            }
        });

    }

    private void initializeUI() {
        placeBtn = findViewById(R.id.Place);
        downloadBtn = findViewById(R.id.Download);
        createBtn = findViewById(R.id.Create);

        pinText = findViewById(R.id.pin);
    }
}
