package com.example.aplikacjawspomagajaca;

public interface QRCodeListener {
    void onQRCodeFound(String qrCode);

    void qrCodeNotFound();
}
