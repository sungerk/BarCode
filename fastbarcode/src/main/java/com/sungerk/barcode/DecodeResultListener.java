package com.sungerk.barcode;

import com.google.android.gms.vision.barcode.internal.NativeBarcode;

import java.util.List;

public interface DecodeResultListener {

   void onDecodeResult(List<NativeBarcode> result);
}
