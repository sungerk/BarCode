package com.com.sungerk.barcode;

import android.graphics.Bitmap;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public final class DecoderCallable implements Callable<List<NativeBarcode>> {

    private NativeBarcodeDetector nativeBarcodeDetector;
    private CameraSource cameraSource;


    public DecoderCallable(CameraSource cameraSource, NativeBarcodeDetector nativeBarcodeDetector) {
        this.cameraSource = cameraSource;
        this.nativeBarcodeDetector = nativeBarcodeDetector;
    }

    private void release() {
        cameraSource.data = null;
    }



    @Override
    public List<NativeBarcode> call() {
        try {

            NativeBarcode[] result = nativeBarcodeDetector.decode(cameraSource.width, cameraSource.height, cameraSource.data);
            if (MyUtils.isNotNull(result)) {
                return Arrays.asList(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release();
        }
        return Collections.emptyList();

    }
}
