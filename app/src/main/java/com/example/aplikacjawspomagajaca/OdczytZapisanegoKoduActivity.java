package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class OdczytZapisanegoKoduActivity extends AppCompatActivity {

    String Nemail = "";
    String NnrTel = "";
    Intent DaneKontaktoweIntent;
    Intent WrocDoKodowIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odczyt_zapisanego_kodu);
        Button przejdzNaStroneSali= (Button) findViewById(R.id.stronaSaliZapKodBtn);
        Button daneKontaktoweBtn = (Button) findViewById(R.id.daneKontaktoweZapKodBtn);
        Button uruchomStroneBtn = (Button) findViewById(R.id.uruchomStroneZapKodBtn);
        przejdzNaStroneSali.setVisibility(View.INVISIBLE);
        daneKontaktoweBtn.setVisibility(View.INVISIBLE);
        uruchomStroneBtn.setVisibility(View.INVISIBLE);
        Button usunZapisanyKod= findViewById(R.id.usunZapisanyKod);
        TextView kodQRTV=(TextView) findViewById(R.id.kodQR);
        kodQRTV.setText(getIntent().getStringExtra("path"));
        String path = this.getFilesDir()+"/"+getIntent().getStringExtra("path")+".txt";
        File kodQR = new File(path);
        String trescKoduQR = "";
        String email = "";
        String nrTel = "";
        if (kodQR != null) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(kodQR));
                String linia;

                while ((linia = reader.readLine()) != null) {
                    trescKoduQR += linia;
                    trescKoduQR += "\n";
                }
                reader.close();
            } catch (Exception e) {
                Log.e("ReadWriteFile", "Nie można odczytać pliku " + path);
            }
            String znacznik = trescKoduQR.substring(0, 4);
            if (znacznik.equals("SUZ_")) {
                String URL = trescKoduQR.substring(4);
                przejdzNaStroneSali.setVisibility(View.VISIBLE);
                przejdzNaStroneSali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                    }
                });

            } else if (znacznik.equals("NUZ_")) {
                String temp1 = trescKoduQR.substring(4, 5);
                String temp2 = trescKoduQR.substring(5, 6);
                String temp3 = trescKoduQR.substring(6, 7);
                String temp4 = trescKoduQR.substring(7, 8);
                String temp5 = trescKoduQR.substring(8, 9);
                int i = 4, j = 5;
                while (!(temp1.equals("-") && temp2.equals("X") && temp3.equals("E") && temp4.equals("M") && temp5.equals("_"))) {
                    temp1 = trescKoduQR.substring(i, j);
                    temp2 = trescKoduQR.substring(i + 1, j + 1);
                    temp3 = trescKoduQR.substring(i + 2, j + 2);
                    temp4 = trescKoduQR.substring(i + 3, j + 3);
                    temp5 = trescKoduQR.substring(i + 4, j + 4);
                    email += temp1;
                    if (trescKoduQR.substring(i - 2, j - 2).equals("p") && trescKoduQR.substring(i - 1, j - 1).equals("l")) {
                        email = email.substring(0, email.length() - 1);
                    }
                    daneKontaktoweBtn.setVisibility(View.VISIBLE);
                    uruchomStroneBtn.setVisibility(View.VISIBLE);
                    if (j + 4 >= trescKoduQR.length() - 4) {
                        Toast.makeText(getApplicationContext(), "Nieprawidłowy kod QR", Toast.LENGTH_SHORT).show();
                        daneKontaktoweBtn.setVisibility(View.INVISIBLE);
                        uruchomStroneBtn.setVisibility(View.INVISIBLE);
                        email = "";
                        break;
                    }
                    i++;
                    j++;
                }
                //-XNR_
                i = i + 4;
                j = j + 4;
                temp1 = trescKoduQR.substring(i, j);
                temp2 = trescKoduQR.substring(i + 1, j + 1);
                temp3 = trescKoduQR.substring(i + 2, j + 2);
                temp4 = trescKoduQR.substring(i + 3, j + 3);
                temp5 = trescKoduQR.substring(i + 4, j + 4);
                while (!(temp1.equals("-") && temp2.equals("X") && temp3.equals("N") && temp4.equals("R") && temp5.equals("_"))) {
                    temp1 = trescKoduQR.substring(i, j);
                    temp2 = trescKoduQR.substring(i + 1, j + 1);
                    temp3 = trescKoduQR.substring(i + 2, j + 2);
                    temp4 = trescKoduQR.substring(i + 3, j + 3);
                    temp5 = trescKoduQR.substring(i + 4, j + 4);
                    nrTel += temp1;
                    if (j + 4 >= trescKoduQR.length() - 4) {
                        Toast.makeText(getApplicationContext(), "Nieprawidłowy kod QR", Toast.LENGTH_SHORT).show();
                        daneKontaktoweBtn.setVisibility(View.INVISIBLE);
                        uruchomStroneBtn.setVisibility(View.INVISIBLE);
                        nrTel = "";
                        break;
                    }
                    daneKontaktoweBtn.setVisibility(View.VISIBLE);
                    uruchomStroneBtn.setVisibility(View.VISIBLE);
                    i++;
                    j++;
                    if (temp1.equals("-")) {
                        nrTel = nrTel.substring(0, nrTel.length() - 1);
                    }
                }
                final String URL = trescKoduQR.substring(i + 4);
                uruchomStroneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                    }
                });

                daneKontaktoweBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        daneKontaktoweAkt();
                    }
                });
                Nemail = email;
                NnrTel = nrTel;

            }
        }
        usunZapisanyKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kodQR.delete();
                if (!kodQR.exists()) {
                    Toast.makeText(OdczytZapisanegoKoduActivity.this, "Poprawnie usunięto kod.", Toast.LENGTH_SHORT).show();
                    wrocDoKodow();
                } else {
                    Toast.makeText(OdczytZapisanegoKoduActivity.this, "Nie można usunąć kodu.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public  void daneKontaktoweAkt(){
        DaneKontaktoweIntent=new Intent(this, DaneKontaktoweActivity.class);
        DaneKontaktoweIntent.putExtra("email", Nemail);
        DaneKontaktoweIntent.putExtra("nrTel", NnrTel);
        startActivity(DaneKontaktoweIntent);

    }
    public void wrocDoKodow(){
        WrocDoKodowIntent= new Intent(this,ZapisaneKodyActivity.class);
        startActivity(WrocDoKodowIntent);
    }
}
