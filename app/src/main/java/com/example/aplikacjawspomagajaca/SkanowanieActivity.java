package com.example.aplikacjawspomagajaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

import com.google.common.util.concurrent.ListenableFuture;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.*;

public class SkanowanieActivity extends AppCompatActivity {

    Intent aktSkanowanieSalaIntent;
    Intent daneKontaktoweIntent;
    Intent zapiszKodNauczIntent;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Button qrCodeFoundButton;
    private String qrCode;
    String Nemail = "";
    String NnrTel = "";
    ArrayList<String> doTosta=new ArrayList<String>();
    ArrayList<String> godzinyZajec=new ArrayList<String>();
    StringBuilder sb;
    String[] words=null;
    String s;
    private Calendar fromTime;
    private Calendar toTime;
    private Calendar currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skanowanie);
        previewView = findViewById(R.id.activity_previewView);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        qrCodeFoundButton = findViewById(R.id.activity_qrCodeFoundButton);
        qrCodeFoundButton.setVisibility(View.INVISIBLE);
        Button skanSalaBtn = findViewById(R.id.skanSalaBtn);
        Button daneKontaktoweBtn = findViewById(R.id.daneKontaktoweBtn);
        daneKontaktoweBtn.setVisibility(View.INVISIBLE);
        skanSalaBtn.setVisibility(View.INVISIBLE);
        Button uruchomStroneBtn = findViewById(R.id.uruchomStroneBtn);
        uruchomStroneBtn.setVisibility(View.INVISIBLE);
        Button zapiszKodBtn = findViewById(R.id.zapiszKodNauczBtn);
        zapiszKodBtn.setVisibility(View.INVISIBLE);
        qrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zapiszKodBtn.setVisibility(View.INVISIBLE);
                uruchomStroneBtn.setVisibility(View.INVISIBLE);
                daneKontaktoweBtn.setVisibility(View.INVISIBLE);
                skanSalaBtn.setVisibility(View.INVISIBLE);
                //parsowanie dla kodu QR (znacznik: SUZ_ -> sala znacznik: NUZ_ -> nauczyciel)
                String znacznik = qrCode.substring(0, 4);
                String email = "";
                String nrTel = "";

                if (znacznik.equals("NUZ_")) {
                    String temp1 = qrCode.substring(4, 5);
                    String temp2 = qrCode.substring(5, 6);
                    String temp3 = qrCode.substring(6, 7);
                    String temp4 = qrCode.substring(7, 8);
                    String temp5 = qrCode.substring(8, 9);
                    int i = 4, j = 5;
                    while (!(temp1.equals("-") && temp2.equals("X") && temp3.equals("E") && temp4.equals("M") && temp5.equals("_"))) {
                        temp1 = qrCode.substring(i, j);
                        temp2 = qrCode.substring(i + 1, j + 1);
                        temp3 = qrCode.substring(i + 2, j + 2);
                        temp4 = qrCode.substring(i + 3, j + 3);
                        temp5 = qrCode.substring(i + 4, j + 4);
                        email += temp1;
                        if (qrCode.substring(i - 2, j - 2).equals("p") && qrCode.substring(i - 1, j - 1).equals("l")) {
                            email = email.substring(0, email.length() - 1);
                        }
                        daneKontaktoweBtn.setVisibility(View.VISIBLE);
                        uruchomStroneBtn.setVisibility(View.VISIBLE);
                        if (j + 4 >= qrCode.length() - 4) {
                            makeText(getApplicationContext(), "Nieprawidłowy kod QR", LENGTH_SHORT).show();
                            daneKontaktoweBtn.setVisibility(View.INVISIBLE);
                            uruchomStroneBtn.setVisibility(View.INVISIBLE);
                            zapiszKodBtn.setVisibility(View.INVISIBLE);
                            email = "";
                            break;
                        }
                        i++;
                        j++;
                    }
                    //-XNR_
                    i = i + 4;
                    j = j + 4;
                    temp1 = qrCode.substring(i, j);
                    temp2 = qrCode.substring(i + 1, j + 1);
                    temp3 = qrCode.substring(i + 2, j + 2);
                    temp4 = qrCode.substring(i + 3, j + 3);
                    temp5 = qrCode.substring(i + 4, j + 4);
                    while (!(temp1.equals("-") && temp2.equals("X") && temp3.equals("N") && temp4.equals("R") && temp5.equals("_"))) {
                        temp1 = qrCode.substring(i, j);
                        temp2 = qrCode.substring(i + 1, j + 1);
                        temp3 = qrCode.substring(i + 2, j + 2);
                        temp4 = qrCode.substring(i + 3, j + 3);
                        temp5 = qrCode.substring(i + 4, j + 4);
                        nrTel += temp1;
                        if (j + 4 >= qrCode.length() - 4) {
                            makeText(getApplicationContext(), "Nieprawidłowy kod QR", LENGTH_SHORT).show();
                            daneKontaktoweBtn.setVisibility(View.INVISIBLE);
                            uruchomStroneBtn.setVisibility(View.INVISIBLE);
                            zapiszKodBtn.setVisibility(View.INVISIBLE);
                            nrTel = "";
                            break;
                        }
                        daneKontaktoweBtn.setVisibility(View.VISIBLE);
                        uruchomStroneBtn.setVisibility(View.VISIBLE);
                        zapiszKodBtn.setVisibility(View.VISIBLE);
                        i++;
                        j++;
                        if (temp1.equals("-")) {
                            nrTel = nrTel.substring(0, nrTel.length() - 1);
                        }

                    }
                    zapiszKodBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zapiszKodNauczAkt(qrCode);
                        }
                    });
                    final String URL = qrCode.substring(i + 4);
                    uruchomStroneBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                URL url=new URL(URL);

                                try (InputStreamReader isr = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
                                     BufferedReader br = new BufferedReader(isr)) {

                                    String line;

                                    sb = new StringBuilder();

                                    while ((line = br.readLine()) != null) {

                                        sb.append(line);
                                        sb.append(System.lineSeparator());
                                    }
                                    File plikHTML=new File(getFilesDir(),"tmp.html");
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(plikHTML, true /*append*/));
                                    new FileOutputStream(plikHTML).close();
                                    FileReader fileRead = new FileReader(plikHTML);
                                    writer.write(sb.toString());

                                    BufferedReader buffRead = new BufferedReader(fileRead);
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    LocalDateTime now = LocalDateTime.now();
                                    String input=dtf.format(now).toString()+"</td>";
                                    File tmp1 = new File(getFilesDir(),"tmp1.html");
                                    BufferedWriter writer1 = new BufferedWriter(new FileWriter(tmp1, true /*append*/));
                                    new FileOutputStream(tmp1).close();
                                    while((s=buffRead.readLine())!=null)
                                    {
                                        words=s.split("<td>");
                                        for (String word : words)
                                        {
                                            if (word.equals(input))
                                            {
                                                writer1.write(s);
                                                writer1.write(System.lineSeparator());
                                                for (int i=0; i<3;i++) { //plan nauczyciela i<3; plan studentów i<4
                                                    s=buffRead.readLine();
                                                    writer1.write(s);
                                                    writer1.write(System.lineSeparator());
                                                }
                                            }
                                        }
                                    }

                                    writer1.close();
                                    FileReader fileR = new FileReader(tmp1);
                                    BufferedReader BuffR = new BufferedReader(fileR);
                                    String liniaPlanu;
                                    int nrLinii=0;
                                    int i=0;
                                    String[] nazwaP=null;
                                    String godzinyZajecTemp="";
                                    String doToastaTemp="";
                                    while((liniaPlanu=BuffR.readLine())!=null)
                                    {

                                        if(nrLinii==1+(4*i))
                                        {
                                            doToastaTemp+=liniaPlanu.substring(liniaPlanu.length()-10,liniaPlanu.length()-5).toString();
                                            doToastaTemp+="-";
                                            godzinyZajecTemp+=liniaPlanu.substring(liniaPlanu.length()-10,liniaPlanu.length()-5).toString();
                                            godzinyZajecTemp+="-";
                                        }
                                        else if(nrLinii==2+(4*i)){
                                            doToastaTemp+=liniaPlanu.substring(liniaPlanu.length()-10,liniaPlanu.length()-5).toString();
                                            doToastaTemp+=" ";
                                            godzinyZajecTemp+=liniaPlanu.substring(liniaPlanu.length()-10,liniaPlanu.length()-5).toString();
                                        }
                                        else if(nrLinii==3+(4*i))
                                        {
                                            nazwaP=liniaPlanu.split("<td>");
                                            doToastaTemp+=nazwaP[1];
                                        }
                                        nrLinii++;
                                        if(nrLinii==4+(4*i)){
                                            doTosta.add(doToastaTemp);
                                            godzinyZajec.add(godzinyZajecTemp);
                                            doToastaTemp="";
                                            godzinyZajecTemp="";
                                            i++;
                                        }
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            for(int j=0;j<doTosta.size();j++){
                                if(sprawdzCzas(godzinyZajec.get(j))){
                                    makeText(SkanowanieActivity.this,doTosta.get(j), LENGTH_LONG).show();
                                    makeText(SkanowanieActivity.this,doTosta.get(j), LENGTH_LONG).show();
                                    makeText(SkanowanieActivity.this,doTosta.get(j), LENGTH_LONG).show();
                                break;}
                                else if(!(j+1<doTosta.size()))makeText(SkanowanieActivity.this,"Aktualnie nie są prowadzone zajęcia.", LENGTH_LONG).show();
                                else if(doTosta.size()==1&&!(sprawdzCzas(godzinyZajec.get(j))))makeText(SkanowanieActivity.this,"Aktualnie nie są prowadzone zajęcia.", LENGTH_LONG).show();
                                }
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                        }
                    });

                    daneKontaktoweBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            daneKontaktoweActivity();
                        }
                    });
                    Nemail = email;
                    NnrTel = nrTel;



                } else if (znacznik.equals("SUZ_")) {
                    makeText(getApplicationContext(), "Wykryto kod sali, można przejść do skanowania kodu sali klikając guzik poniżej", LENGTH_LONG).show();
                    skanSalaBtn.setVisibility(View.VISIBLE);
                    skanSalaBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            nowaAkt();
                        }
                    });
                } else {
                    makeText(getApplicationContext(), "Nieodpowiedni kod QR", LENGTH_SHORT).show();
                }
            }
        });
        startCamera();
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                makeText(this, "Problem z uruchomieniem kamery " + e.getMessage(), LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeAnalyzer(new QRCodeListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {
                qrCode = _qrCode;
                qrCodeFoundButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void qrCodeNotFound() {
                qrCodeFoundButton.setVisibility(View.INVISIBLE);
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
    }

    public void nowaAkt() { //aktywność od skanowania kodu sali
        aktSkanowanieSalaIntent = new Intent(this, SkanowanieSalaActivity1.class);
        finish();
        startActivity(aktSkanowanieSalaIntent);
    }

    public void daneKontaktoweActivity() { //aktywność przenosząca do danych kontaktowych

        daneKontaktoweIntent = new Intent(this, DaneKontaktoweActivity.class);
        daneKontaktoweIntent.putExtra("email", Nemail);
        daneKontaktoweIntent.putExtra("nrTel", NnrTel);
        startActivity(daneKontaktoweIntent);
    }

    public void zapiszKodNauczAkt(String kod) {
        zapiszKodNauczIntent = new Intent(this, DialogZapisActivity.class);
        zapiszKodNauczIntent.putExtra("kod", kod);
        startActivity(zapiszKodNauczIntent);
    }
    public boolean sprawdzCzas(String time) {
        try {
            String[] times = time.split("-");
            String[] from = times[0].split(":");
            String[] until = times[1].split(":");

            fromTime = Calendar.getInstance();
            fromTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from[0]));
            fromTime.set(Calendar.MINUTE, Integer.valueOf(from[1]));

            toTime = Calendar.getInstance();
            toTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until[0]));
            toTime.set(Calendar.MINUTE, Integer.valueOf(until[1]));

            currentTime = Calendar.getInstance();
            if(currentTime.after(fromTime) && currentTime.before(toTime)){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}