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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotatkiActivity extends AppCompatActivity {

    Intent nowaNotatkaIntent;
    Intent edytujNotatkeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki);
        String path = this.getExternalFilesDir(null).toString();
        File directory = new File(path);
        ArrayList<String> listaNotatekString = new ArrayList<String>(Arrays.asList(directory.list()));
        for (int i = 0; i < listaNotatekString.size(); i++) {
            listaNotatekString.set(i, listaNotatekString.get(i).substring(0, listaNotatekString.get(i).length() - 4));
        }
        ListView listView = (ListView) findViewById(R.id.listaNotatek);
        CustomListAdapter listAdapter = new CustomListAdapter(NotatkiActivity.this, R.layout.custom_list, listaNotatekString );
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                otworzNotatkeAkt(item);
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
        nowaNotatkaIntent = new Intent(this, NowaNotatkaActivity.class);
        finish();
        startActivity(nowaNotatkaIntent);

    }

    public void otworzNotatkeAkt(String plik) {
        edytujNotatkeIntent = new Intent(this, EdytujNotatkeActivity.class);
        edytujNotatkeIntent.putExtra("nazwaPliku", plik);
        finish();
        startActivity(edytujNotatkeIntent);
    }


}
