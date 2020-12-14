package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotatkiActivity extends AppCompatActivity {

    Intent nowaNotatkaIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki);
        Button nowaNotatkaBtn= (Button) findViewById(R.id.nowaNotatkaBtn);
        nowaNotatkaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowaNotatkaAkt();
            }
        });

    }
    public void nowaNotatkaAkt(){
        nowaNotatkaIntent=new Intent(this, nowaNotatkaActivity.class );
        startActivity(nowaNotatkaIntent);
    }
}
