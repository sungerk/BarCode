package com.example.sunger.ml

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.vision.barcode.internal.FileUtils
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector
import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode

import java.text.SimpleDateFormat
import java.util.Date
import android.content.Context.MODE_PRIVATE
import com.com.sungerk.barcode.FastBarcode
import com.com.sungerk.barcode.Utils
import com.google.android.gms.vision.barcode.internal.NativeBarcode
import java.io.File

//https://www.cnblogs.com/yichouangle/p/3150603.html
class MainActivity : AppCompatActivity(), FireBaseBarcodeScannerView.ResultHandler {
    private lateinit var mScannerView: FireBaseBarcodeScannerView
    private lateinit var textView: TextView
    private lateinit var soundManager: SoundManager

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        FastBarcode.getInstance().initialize(this)
        setContentView(R.layout.activity_main)

//        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bar)
//        NativeBarcodeDetector(BarcodeDetectorOptions()).decode(bitmap)
//


        soundManager = SoundManager(this, R.raw.hsm_beep)
        mScannerView = findViewById(R.id.fireBaseBarcodeScannerView)
        textView = findViewById(R.id.textView)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openScanner()
        }
    }

    private fun openScanner() {
        mScannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView.startCamera()          // Start camera on resume
        mScannerView.setAutoFocus(true)
        mScannerView.setAspectTolerance(0.5f)
    }

    public override fun onResume() {
        super.onResume()
        openScanner()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()           // Stop camera on pause
    }


    override fun handleResult(rawResult: List<NativeBarcode>) {
        soundManager.play(0)
        val stringBuffer = StringBuffer("扫描结果")
        stringBuffer.append("\n")
        stringBuffer.append("扫描时间:")
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        println(df.format(Date()))
        stringBuffer.append(df.format(Date()))
        for (bar in rawResult) {
            stringBuffer.append("\n")
            stringBuffer.append("扫描结果:")
            stringBuffer.append(bar.displayValue)
        }
        textView!!.text = stringBuffer.toString()

    }


    override fun onDestroy() {
        super.onDestroy()
        soundManager.release()
    }
}
