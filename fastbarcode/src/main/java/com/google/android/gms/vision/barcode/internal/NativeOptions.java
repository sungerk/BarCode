package com.google.android.gms.vision.barcode.internal;


import android.support.annotation.Keep;
import com.sungerk.barcode.BarcodeFormat;
@Keep
class NativeOptions {
    public int barcodeFormats = BarcodeFormat.ALL_FORMATS;

    NativeOptions() {
    }
}
