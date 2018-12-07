package com.sungerk.barcode;

import com.google.android.gms.vision.barcode.internal.NativeBarcode;

import java.util.List;

public interface DecoderCallback {

    void onResult(List<NativeBarcode> nativeBarcodes);
}
