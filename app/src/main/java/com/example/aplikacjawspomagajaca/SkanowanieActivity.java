package com.example.aplikacjawspomagajaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class SkanowanieActivity extends AppCompatActivity {

    Intent aktSkanowanieSalaIntent;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Button qrCodeFoundButton;
    private String qrCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skanowanie);
        previewView = findViewById(R.id.activity_previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        qrCodeFoundButton = findViewById(R.id.activity_qrCodeFoundButton);
        qrCodeFoundButton.setVisibility(View.INVISIBLE);
        Button skanSalaBtn=findViewById(R.id.skanSalaBtn);
        skanSalaBtn.setVisibility(View.INVISIBLE);
        qrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //parsowanie dla kodu QR (znacznik: SUZ_ -> sala znacznik: NUZ_ -> nauczyciel)
                String znacznik=qrCode.substring(0,4);
                String email="";
                String nrTel="";

                if(znacznik.equals("NUZ_")){
                    String temp1=qrCode.substring(4,5);
                    String temp2=qrCode.substring(5,6);
                    String temp3=qrCode.substring(6,7);
                    String temp4=qrCode.substring(7,8);
                    String temp5=qrCode.substring(8,9);
                    int i=4, j=5;
                    while(!temp1.equals("-")&&!temp2.equals("X")&&!temp3.equals("E")&&!temp4.equals("M")&&!temp5.equals("_")){
                        System.out.println(temp1);
                        temp1=qrCode.substring(i,j);
                        temp2=qrCode.substring(i+1,j+1);
                        temp3=qrCode.substring(i+2,j+2);
                        temp4=qrCode.substring(i+3,j+3);
                        temp5=qrCode.substring(i+4,j+4);
                        email+=temp1;
                        if(qrCode.substring(i-2,j-2).equals("p")&&qrCode.substring(i-1,j-1).equals("l")){
                            email=email.substring(0, email.length() - 1); }
                        i++;
                        j++;
                    }
                    Toast.makeText(getApplicationContext(), "Jej zczytałeś email, email: "+email, Toast.LENGTH_LONG).show();
                   // String URL=qrCode.substring(4);
                    //Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_SHORT).show();
                    //Log.i(MainActivity.class.getSimpleName(), "QR Code Found: " + qrCode);
                   // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                }else if(znacznik.equals("SUZ_")) {
                    Toast.makeText(getApplicationContext(), "Wykryto kod sali, można przejść do skanowania kodu sali klikając guzik poniżej", Toast.LENGTH_LONG).show();
                    skanSalaBtn.setVisibility(View.VISIBLE);
                    skanSalaBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            nowaAkt();
                        }
                    });
                } else{ Toast.makeText(getApplicationContext(), "Nieodpowiedni kod QR", Toast.LENGTH_SHORT).show(); }
            }});
        startCamera();
    }
    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }
    public void nowaAkt(){ //aktywność od skanowania kodu sali
        aktSkanowanieSalaIntent= new Intent(this, SkanowanieSalaActivity1.class);
        startActivity(aktSkanowanieSalaIntent);
    }
}