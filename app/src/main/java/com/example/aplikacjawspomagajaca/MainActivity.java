package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnZeskanujKod;
    Intent aktSkanowanieIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnZeskanujKod=(Button) findViewById(R.id.btnZeskanujKod);
        btnZeskanujKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowaAkt();
            }
        });
    }
    public void nowaAkt(){
        aktSkanowanieIntent= new Intent(this, SkanowanieActivity.class);
        startActivity(aktSkanowanieIntent);
    }
}