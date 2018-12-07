package com.google.android.gms.vision.barcode.internal;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

public class NativeBarcodeDetector {

    private NativeOptions nativeOptions = new NativeOptions();

    static native NativeBarcode[] recognizeBitmapNative(Bitmap bitmap, NativeOptions nativeOptions);

    static native NativeBarcode[] recognizeBufferNative(int width, int height, ByteBuffer byteBuffer, NativeOptions nativeOptions);

    static native NativeBarcode[] recognizeNative(int width, int height, byte[] bArr, NativeOptions nativeOptions);


    public void setSupportFormats(int barcodeFormats) {
        nativeOptions.barcodeFormats = barcodeFormats;
    }


    public NativeBarcode[] decode(Bitmap bitmap) {
        return recognizeBitmapNative(bitmap, nativeOptions);
    }


    public NativeBarcode[] decode(int width, int height, byte[] bArr) {
        return recognizeNative(width, height, bArr, nativeOptions);
    }

    public NativeBarcode[] decode(int width, int height, ByteBuffer byteBuffer) {
        return recognizeBufferNative(width, height, byteBuffer, nativeOptions);
    }
}
