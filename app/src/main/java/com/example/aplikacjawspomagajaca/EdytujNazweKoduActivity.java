package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EdytujNazweKoduActivity extends AppCompatActivity {

    Intent wroc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_nazwe_kodu);
        Button zapiszKod = findViewById(R.id.zapiszKodDialogBtn);
        EditText nazwaKoduPole = findViewById(R.id.nazwaKodu);
        String kod = getIntent().getStringExtra("kod");
        String nazwa=getIntent().getStringExtra("nazwaKodu");
        nazwaKoduPole.setText(nazwa);
        zapiszKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //po zapisie kodu wracamy do poprzedniej aktywności
                String nazwaKodu = nazwaKoduPole.getText().toString();
                if(!nazwaKodu.equals(nazwa)){
                    try {
                        File poprzedniKod=new File(getFilesDir() + "/" + nazwa + ".txt");
                        poprzedniKod.delete();
                        File kodDoZapisu = new File(getFilesDir() + "/" + nazwaKodu + ".txt");
                        if (!kodDoZapisu.exists()) {
                            kodDoZapisu.createNewFile();
                            BufferedWriter writer = new BufferedWriter(new FileWriter(kodDoZapisu, true /*append*/));
                            writer.write(kod);
                            writer.close();
                            MediaScannerConnection.scanFile(EdytujNazweKoduActivity.this,
                                    new String[]{kodDoZapisu.toString()},
                                    null,
                                    null);
                            Toast.makeText(EdytujNazweKoduActivity.this, "Kod " + nazwaKodu + " został zapisany.", Toast.LENGTH_SHORT).show();
                        } else {
                            kodDoZapisu.delete();
                            BufferedWriter writer = new BufferedWriter(new FileWriter(kodDoZapisu, true /*append*/));
                            writer.write(kod);
                            writer.close();
                            MediaScannerConnection.scanFile(EdytujNazweKoduActivity.this,
                                    new String[]{kodDoZapisu.toString()},
                                    null,
                                    null);
                            Toast.makeText(EdytujNazweKoduActivity.this, "Kod " + nazwaKodu + " został nadpisany", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        Log.e("Exception", "Błąd zapisu pliku " + e.toString());
                    }
                    wroc();
                    }
                else Toast.makeText(EdytujNazweKoduActivity.this, "Nazwa pozostała bez zmian!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void wroc(){
       wroc = new Intent (this, ZapisaneKodyActivity.class);
       finish();
       startActivity(wroc);

    }
}