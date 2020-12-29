package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

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

public class DialogZapisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_zapis);
        Button zapiszKod = findViewById(R.id.zapiszKodDialogBtn);
        EditText nazwaKoduPole = findViewById(R.id.nazwaKodu);
        String kod = getIntent().getStringExtra("kod");
        zapiszKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //po zapisie kodu wracamy do poprzedniej aktywności
                String nazwaKodu = nazwaKoduPole.getText().toString();
                try {
                    File kodDoZapisu = new File(getFilesDir() + "/" + nazwaKodu + ".txt");
                    if (!kodDoZapisu.exists()) {
                        kodDoZapisu.createNewFile();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(kodDoZapisu, true /*append*/));
                        writer.write(kod);
                        writer.close();
                        MediaScannerConnection.scanFile(DialogZapisActivity.this,
                                new String[]{kodDoZapisu.toString()},
                                null,
                                null);
                        Toast.makeText(DialogZapisActivity.this, "Kod " + nazwaKodu + " został zapisany.", Toast.LENGTH_SHORT).show();
                    } else {
                        kodDoZapisu.delete();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(kodDoZapisu, true /*append*/));
                        writer.write(kod);
                        writer.close();
                        MediaScannerConnection.scanFile(DialogZapisActivity.this,
                                new String[]{kodDoZapisu.toString()},
                                null,
                                null);
                        Toast.makeText(DialogZapisActivity.this, "Kod " + nazwaKodu + " został nadpisany", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    Log.e("Exception", "Błąd zapisu pliku " + e.toString());
                }
                finish();
            }
        });
    }
}