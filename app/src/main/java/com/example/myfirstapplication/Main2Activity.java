package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView name;
    TextView position;

    String data1, data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        name = findViewById(R.id.country);
        position = findViewById(R.id.capitale);

        data1 = intent.getStringExtra("pays");
        data2 = intent.getStringExtra("capitale");

        name.setText(data1);
        position.setText(data2);
    }
}
