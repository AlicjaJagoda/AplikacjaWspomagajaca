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

public class SkanowanieSalaActivity1 extends AppCompatActivity {

    Intent aktSkanowanieIntent;
    Intent zapiszKodSaliIntent;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Button qrCodeFoundButton;
    private String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skanowanie_sala1);
        previewView = findViewById(R.id.activity_previewView1);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        qrCodeFoundButton = findViewById(R.id.activity_qrCodeFoundButton1);
        qrCodeFoundButton.setVisibility(View.INVISIBLE);
        Button skanNauBtn = findViewById(R.id.skanNauBtn);
        skanNauBtn.setVisibility(View.INVISIBLE);
        Button zapiszKodSali = findViewById(R.id.zapiszKodSalaBtn);
        Button przejdzNaStroneSali = findViewById(R.id.przejdzNaPlanSaliBtn);
        zapiszKodSali.setVisibility(View.INVISIBLE);
        przejdzNaStroneSali.setVisibility(View.INVISIBLE);
        qrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zapiszKodSali.setVisibility(View.INVISIBLE);
                przejdzNaStroneSali.setVisibility(View.INVISIBLE);
                skanNauBtn.setVisibility(View.INVISIBLE);
                //parsowanie dla kodu QR (znacznik: SUZ_ -> sala znacznik: NUZ_ -> nauczyciel)
                String znacznik = qrCode.substring(0, 4);
                if (znacznik.equals("SUZ_")) {
                    String URL = qrCode.substring(4);
                    zapiszKodSali.setVisibility(View.VISIBLE);
                    przejdzNaStroneSali.setVisibility(View.VISIBLE);
                    przejdzNaStroneSali.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                        }
                    });
                    zapiszKodSali.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zapiszKodSaliAkt(qrCode);

                        }
                    });

                } else if (znacznik.equals("NUZ_")) {
                    Toast.makeText(getApplicationContext(), "Wykryto kod nauczyciela, można przejść do skanowania kodu nauczyciela klikając guzik poniżej", Toast.LENGTH_LONG).show();
                    skanNauBtn.setVisibility(View.VISIBLE);
                    skanNauBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            nowaAkt();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Nieodpowiedni kod QR", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Błąd przy uruchomieniu kamery " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }

    public void nowaAkt() { //aktywność od skanowanie kodu nauczyciela
        aktSkanowanieIntent = new Intent(this, SkanowanieActivity.class);
        finish();
        startActivity(aktSkanowanieIntent);
    }

    public void zapiszKodSaliAkt(String kod) {
        zapiszKodSaliIntent = new Intent(this, DialogZapisActivity.class);
        zapiszKodSaliIntent.putExtra("kod", kod);
        startActivity(zapiszKodSaliIntent);
    }
}