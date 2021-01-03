package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EdytujNotatkeActivity extends AppCompatActivity {


    Intent NotatkiIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_notatke);
        EditText tytNotatkiEdytuj = findViewById(R.id.tytulNotatkiEdytuj);
        EditText treNotatkiEdytuj = findViewById(R.id.trescNotatkiEdytuj);
        Button zapiszBtn = findViewById(R.id.zapiszEdycjeBtn);
        Button usunBtn = findViewById(R.id.usunNotatke);
        Button wrocBtn = findViewById(R.id.wrocEdycjaBtn);
        String nazwaPliku = getIntent().getStringExtra("nazwaPliku");
        String trescNotatki = "";
        File notatka = new File(this.getExternalFilesDir(null), nazwaPliku + ".txt");
        if (notatka != null) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(notatka));
                String linia;

                while ((linia = reader.readLine()) != null) {
                    trescNotatki += linia;
                    trescNotatki += "\n";
                }
                reader.close();
            } catch (Exception e) {
                Log.e("ReadWriteFile", "Nie można odczytać pliku " + nazwaPliku);
            }
            tytNotatkiEdytuj.setText(nazwaPliku);
            treNotatkiEdytuj.setText(trescNotatki);
        }
        wrocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrocDoNotatek();
            }
        });
        usunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notatka.delete();
                if (!notatka.exists()) {
                    Toast.makeText(EdytujNotatkeActivity.this, "Poprawnie usunięto notatkę.", Toast.LENGTH_SHORT).show();
                    wrocDoNotatek();
                } else {
                    Toast.makeText(EdytujNotatkeActivity.this, "Nie można usunąć notatki.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        zapiszBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tytNotatkiEdytuj.getText().toString().equals("")) {
                    try {
                        notatka.delete();
                        File notatkaPoEdycji = new File(EdytujNotatkeActivity.this.getExternalFilesDir(null), tytNotatkiEdytuj.getText().toString() + ".txt");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(notatkaPoEdycji, true /*append*/));
                        writer.write(treNotatkiEdytuj.getText().toString());
                        writer.close();
                        MediaScannerConnection.scanFile(EdytujNotatkeActivity.this,
                                new String[]{notatkaPoEdycji.toString()},
                                null,
                                null);
                        Toast.makeText(EdytujNotatkeActivity.this, "Notatka " + tytNotatkiEdytuj.getText().toString() + ".txt została zedytowana", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        Log.e("Exception", "Błąd zapisu pliku " + e.toString());
                    }
                } else
                    Toast.makeText(EdytujNotatkeActivity.this, "Nie podano tytułu!", Toast.LENGTH_SHORT).show();
            }
        });


    }
        public void wrocDoNotatek(){
            NotatkiIntent=new Intent(this, NotatkiActivity.class);
            startActivity(NotatkiIntent);
            finish();

        }
}