package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView pays;
    TextView capital;
    TextView latitude;
    TextView longitude;
    TextView surface_totale;
    TextView num_id;



    String data1, data2,data3,data4,data5,data6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        pays = findViewById(R.id.country);
        capital = findViewById(R.id.capitale);
        latitude =findViewById(R.id.latitude);
        longitude =findViewById(R.id.longitude);
        surface_totale =findViewById(R.id.surface_totale);
        num_id =findViewById(R.id.num_id);





        data1 = intent.getStringExtra("pays");
        data2 = intent.getStringExtra("capitale");
        data3 = intent.getStringExtra("latitude");
        data4  = intent.getStringExtra("longitude");
        data5  = intent.getStringExtra("surface_totale");
        data6  = intent.getStringExtra("num_id");



        pays.setText(data1);
        capital.setText(data2);
        latitude.setText(data3);
        longitude.setText(data4);
        surface_totale.setText(data5);
        num_id.setText(data6);


    }
}
