Introduction
============

Android library projects that provides fast scan barcode
Decompile by Google Service visio.

Screenshots
===========
<img src="https://github.com/sungerk/BarCode/blob/dev/shortcuts/barcode.gif">


Simple Usage
------------

1.) Add camera permission to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

2.) A very basic activity would look like this:

```java
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


```

Please take a look at the [Demo](https://github.com/sungerk/BarCode/blob/dev/app/src/main/java/com/sungerk/example/MainActivity.java) project for a full working example.


Interesting methods on the ZXingScannerView include:

```java
// Toggle flash:
void setFlash(boolean);

// Toogle autofocus:
void setAutoFocus(boolean);

// Specify interested barcode formats:
void setFormats(List<BarcodeFormat> formats);

// Specify the cameraId to start with:
void startCamera(int cameraId);
```

Specify front-facing or rear-facing cameras by using the `void startCamera(int cameraId);` method.


For HUAWEI mobile phone like P9, P10, when scanning using the default settings, it won't work due to the
"preview size",  please adjust the parameter as below:

```java
mScannerView = (FastScannerView) findViewById(R.id.fastScannerView);

// this paramter will make your HUAWEI phone works great!
mScannerView.setAspectTolerance(0.5f);
```

Supported Formats:

```java
BarcodeFormat.CODE_128
BarcodeFormat.CODE_39
BarcodeFormat.CODE_93
BarcodeFormat.CODABAR
BarcodeFormat.DATA_MATRIX
BarcodeFormat.EAN_13
BarcodeFormat.EAN_8
BarcodeFormat.ITF
BarcodeFormat.QR_CODE
BarcodeFormat.UPC_A
BarcodeFormat.UPC_E
BarcodeFormat.PDF417
BarcodeFormat.AZTEC
BarcodeFormat.CONTACT_INFO
BarcodeFormat.EMAIL
BarcodeFormat.ISBN
BarcodeFormat.PHONE
BarcodeFormat.PRODUCT
BarcodeFormat.SMS
BarcodeFormat.TEXT
BarcodeFormat.GEO
BarcodeFormat.TEXT
BarcodeFormat.CALENDAR_EVENT
BarcodeFormat.DRIVER_LICENSE
```

Demo APK
--------------
Android APK can be downloaded [here](https://github.com/sungerk/BarCode/blob/dev/apk/app-release.apk).


### License

```
Copyright (c) 2018 sungerk
Licensed under LGP3
```
