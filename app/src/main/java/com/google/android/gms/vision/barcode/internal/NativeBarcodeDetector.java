package com.google.android.gms.vision.barcode.internal;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;

import java.nio.ByteBuffer;

public class NativeBarcodeDetector {

    private NativeOptions nativeOptions = new NativeOptions();

    static native NativeBarcode[] recognizeBitmapNative(Bitmap bitmap, NativeOptions nativeOptions);

    static native NativeBarcode[] recognizeBufferNative(int width, int height, ByteBuffer byteBuffer, NativeOptions nativeOptions);

    static native NativeBarcode[] recognizeNative(int width, int height, byte[] bArr, NativeOptions nativeOptions);

    public NativeBarcodeDetector(BarcodeDetectorOptions barcodeDetectorOptions) {
        this.nativeOptions.barcodeFormats = barcodeDetectorOptions.f8194b;
    }


    public void decode(Bitmap bitmap) {
        Barcode aaac;
        Object[] aaa = recognizeBitmapNative(bitmap, nativeOptions);
        Log.d("sungerk", aaa.length + "");
    }


    public NativeBarcode[] decode(int width, int height, byte[] bArr) {
        NativeBarcode[] aaa = recognizeNative(width, height, bArr, nativeOptions);
        return aaa;
    }

    public void decode(int width, int height, ByteBuffer byteBuffer) {
        Barcode aaac;
        Object[] aaa = recognizeBufferNative(width, height, byteBuffer, nativeOptions);
        Log.d("sungerk", aaa.length + "");
    }
}
