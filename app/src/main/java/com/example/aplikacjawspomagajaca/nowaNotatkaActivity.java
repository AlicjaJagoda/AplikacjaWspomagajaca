package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class nowaNotatkaActivity extends AppCompatActivity {

    Intent zapiszIntent;
    Intent wrocIntent;
    String tytulNotatkiZapis;
    String trescNotatkiZapis;
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

    public void zapiszNotatke(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }


        EditText tytulNotatkiEditText = findViewById(R.id.tytulNotatki);
        tytulNotatkiZapis=tytulNotatkiEditText.getText().toString();
        EditText trescNotatkiEditText = findViewById(R.id.trescNotatki);
        trescNotatkiZapis=trescNotatkiEditText.getText().toString();
        try {
            File notatka = new File(this.getExternalFilesDir(null), tytulNotatkiZapis+".txt");
            if (!notatka.exists())
                notatka.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(notatka, true /*append*/));
            writer.write(trescNotatkiZapis);
            writer.close();
            MediaScannerConnection.scanFile(this,
                    new String[]{notatka.toString()},
                    null,
                    null);
          // Toast.makeText(this, "Notatka "+tytulNotatkiZapis+".txt została zapisana.", Toast.LENGTH_SHORT).show();
           Toast.makeText(this, this.getExternalFilesDir(null).toString(), Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Log.e("Exception", "Błąd zapisu pliku " + e.toString());
        }

    }
    public void wrocDoNotatek(){
        wrocIntent=new Intent(this, NotatkiActivity.class);
        startActivity(wrocIntent);
    }

}