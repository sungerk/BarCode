package com.sungerk.example;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.example.sunger.ml.R;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.sungerk.barcode.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DecodeResultListener {

    private FastScannerView mScannerView;
    private TextView textView;
    private SoundManager soundManager;
    private DecoderManager decoderManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decoderManager = FastBarcode.getInstance().getDecoderManager();
        setContentView(com.example.sunger.ml.R.layout.activity_main);
        soundManager = new SoundManager(this, com.example.sunger.ml.R.raw.hsm_beep);
        mScannerView = findViewById(com.example.sunger.ml.R.id.fastScannerView);
        mScannerView.setFormats(Arrays.asList(BarcodeFormat.CODE_39, BarcodeFormat.CODE_128,BarcodeFormat.QR_CODE));
        textView = findViewById(R.id.textView);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openScanner();
        }
    }


    private void openScanner() {
        mScannerView.startCamera();
        mScannerView.setAutoFocus(true);
        mScannerView.setAspectTolerance(0.5f);
        decoderManager.addResultListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        openScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        decoderManager.removeResultListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }

    @Override
    public void onDecodeResult(List<NativeBarcode> result) {
        soundManager.play(0);
        StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append("Scan Time:");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringBuffer.append(df.format(new Date()));
        for (NativeBarcode bar : result) {
            stringBuffer.append("\n");
            stringBuffer.append("Result:\n");
            stringBuffer.append("     "+bar.displayValue);
        }
        textView.setText(stringBuffer.toString());

    }


}
