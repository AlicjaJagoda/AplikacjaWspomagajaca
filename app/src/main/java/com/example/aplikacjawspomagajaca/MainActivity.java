package com.example.aplikacjawspomagajaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    Button btnZeskanujKod;
    Button skanujSalaBtn;
    Intent aktSkanowanieIntent;
    Intent aktSkanowanieSalaIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnZeskanujKod=(Button) findViewById(R.id.btnZeskanujKod);
        btnZeskanujKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCamera();
                nowaAkt();
            }
        });
        skanujSalaBtn=(Button) findViewById(R.id.skanujSalaBtn);
        skanujSalaBtn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                requestCamera();
                nowaAkt1();
            }

        });

    }
    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
   // private void startCamera() {

   // }

    public void nowaAkt(){ //aktywność od skanowanie kodu nauczyciela
        aktSkanowanieIntent= new Intent(this, SkanowanieActivity.class);
        startActivity(aktSkanowanieIntent);
    }
    public void nowaAkt1(){ //aktywność od skanowania kodu sali
        aktSkanowanieSalaIntent= new Intent(this, SkanowanieSalaActivity1.class);
        startActivity(aktSkanowanieSalaIntent);
    }
}