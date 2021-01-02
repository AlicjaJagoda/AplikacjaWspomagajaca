package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;

public class NotatkiActivity extends AppCompatActivity {

    Intent nowaNotatkaIntent;
    Intent edytujNotatkeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki);
        String path = this.getExternalFilesDir(null).toString();
        File directory = new File(path);
        String[] listaNotatekString = directory.list();
        for (int i = 0; i < listaNotatekString.length; i++) {
            listaNotatekString[i] = listaNotatekString[i].substring(0, listaNotatekString[i].length() - 4);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, listaNotatekString);

        ListView listView = (ListView) findViewById(R.id.listaNotatek);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                otworzNotatkeAkt(item);
                finish();
            }
        });


        Button nowaNotatkaBtn = (Button) findViewById(R.id.nowaNotatkaBtn);
        nowaNotatkaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowaNotatkaAkt();
            }
        });


    }


    public void nowaNotatkaAkt() {
        finish();
        nowaNotatkaIntent = new Intent(this, NowaNotatkaActivity.class);
        startActivity(nowaNotatkaIntent);

    }

    public void otworzNotatkeAkt(String plik) {
        edytujNotatkeIntent = new Intent(this, EdytujNotatkeActivity.class);
        edytujNotatkeIntent.putExtra("nazwaPliku", plik);
        startActivity(edytujNotatkeIntent);
    }


}
