package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ZapisaneKodyActivity extends AppCompatActivity {

    Intent otworzKodIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapisane_kody);
        String path = this.getFilesDir().toString();
        File directory = new File(path);
        ArrayList<String> listaKodowString =  new ArrayList<String>(Arrays.asList(directory.list()));
        for (int i = 0; i < listaKodowString.size(); i++) {
            listaKodowString.set(i, listaKodowString.get(i).substring(0, listaKodowString.get(i).length() - 4));
        }
        ListView listView = (ListView) findViewById(R.id.listaKodow);
        CustomListAdapter listAdapter = new CustomListAdapter(ZapisaneKodyActivity.this, R.layout.custom_list, listaKodowString );
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                otworzKod(item);
            }
        });
    }
    public void otworzKod(String plik){
      otworzKodIntent = new Intent(this, OdczytZapisanegoKoduActivity.class);
      otworzKodIntent.putExtra("path", plik);
      startActivity(otworzKodIntent);
    }

}