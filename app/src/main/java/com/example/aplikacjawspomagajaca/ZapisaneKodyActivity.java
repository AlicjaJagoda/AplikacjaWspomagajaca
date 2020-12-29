package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class ZapisaneKodyActivity extends AppCompatActivity {

    Intent otworzKodIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapisane_kody);
        String path = this.getFilesDir().toString();
        File directory = new File(path);
        String[] listaKodowString = directory.list();
        for (int i = 0; i < listaKodowString.length; i++) {
            listaKodowString[i] = listaKodowString[i].substring(0, listaKodowString[i].length() - 4);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, listaKodowString);

        ListView listView = (ListView) findViewById(R.id.listaKodow);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                otworzKod(item);
                finish();
            }
        });
    }
    public void otworzKod(String plik){
      otworzKodIntent = new Intent(this, OdczytZapisanegoKoduActivity.class);
      otworzKodIntent.putExtra("path", plik);
      startActivity(otworzKodIntent);
    }

}