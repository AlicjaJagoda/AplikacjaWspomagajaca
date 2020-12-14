package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class nowaNotatkaActivity extends AppCompatActivity {

    Intent zapiszIntent;
    Intent wrocIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_notatka);
        Button zapiszBtn = findViewById(R.id.zapiszBtn);
        Button wrocBtn = findViewById(R.id.wrocBtn);
        zapiszBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zapiszNotatke();
            }
        });
        wrocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrocDoNotatek();
            }
        });
    }
    //TODO: zapiszNotatke()
    public void zapiszNotatke(){

    }
    public void wrocDoNotatek(){
        wrocIntent=new Intent(this, NotatkiActivity.class);
        startActivity(wrocIntent);
    }

}