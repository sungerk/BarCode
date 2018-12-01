package com.example.sunger.ml;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.com.sungerk.barcode.DecodeResultListener;
import com.com.sungerk.barcode.DecoderManager;
import com.com.sungerk.barcode.FastBarcode;
import com.com.sungerk.barcode.FastScannerView;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;

import java.text.SimpleDateFormat;
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
        setContentView(R.layout.activity_main);
        soundManager = new SoundManager(this, R.raw.hsm_beep);
        mScannerView = findViewById(R.id.fireBaseBarcodeScannerView);
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
        StringBuffer stringBuffer = new StringBuffer("扫描结果");
        stringBuffer.append("\n");
        stringBuffer.append("扫描时间:");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringBuffer.append(df.format(new Date()));
        for (NativeBarcode bar : result) {
            stringBuffer.append("\n");
            stringBuffer.append("扫描结果:");
            stringBuffer.append(bar.displayValue);
        }
        textView.setText(stringBuffer.toString());

    }

    public native void salt(long matAddrGray, int nbrElem);

}
