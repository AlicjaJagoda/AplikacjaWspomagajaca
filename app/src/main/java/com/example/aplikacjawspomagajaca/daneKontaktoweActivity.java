package com.example.aplikacjawspomagajaca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.ACTION_DIAL;

public class daneKontaktoweActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dane_kontaktowe);
        final Button wybierzNrBtn= findViewById(R.id.wybierzNrBtn);
        wybierzNrBtn.setVisibility(View.VISIBLE);
        final Button wyslijEmailBtn= findViewById(R.id.wyslijEmailBtn);
        String email = getIntent().getStringExtra("email");
        String nrTel= getIntent().getStringExtra("nrTel");
        TextView emailText= (TextView) findViewById(R.id.emailText);
        TextView nrTelText= (TextView) findViewById(R.id.nrTelText);
        emailText.setText("Email: "+email);
        nrTelText.setText("Nr telefonu: " +nrTel);
        wybierzNrBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                zadzwon(nrTel);
            }

        });
        wyslijEmailBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                wyslijEmail(email);
                Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
            }

        });

    }
    public void zadzwon(String nrTel){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    10);
            return;
        }else {
            try{
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+nrTel)));
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"Brak uprawnień",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void wyslijEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email} );
        startActivity(Intent.createChooser(intent, "Wyślij wiadomość"));
    }
}