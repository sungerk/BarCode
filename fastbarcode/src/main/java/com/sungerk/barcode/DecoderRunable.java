package com.sungerk.barcode;

import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DecoderRunable implements Runnable {
    private static int timeout = 250;

    private NativeBarcodeDetector nativeBarcodeDetector;
    private CameraSource cameraSource;
    private DecoderCallback decoderCallback;
    private ThreadPoolExecutor threadPoolExecutor;


    public DecoderRunable(NativeBarcodeDetector nativeBarcodeDetector, CameraSource cameraSource, DecoderCallback decoderCallback, ThreadPoolExecutor threadPoolExecutor) {
        this.nativeBarcodeDetector = nativeBarcodeDetector;
        this.cameraSource = cameraSource;
        this.decoderCallback = decoderCallback;
        this.threadPoolExecutor = threadPoolExecutor;
    }



    @Override
    public void run() {
        FutureTask<List<NativeBarcode>> futureTask = new FutureTask(new DecoderCallable(cameraSource,nativeBarcodeDetector));
        threadPoolExecutor.submit(futureTask);
        try {
            List<NativeBarcode> nativeBarcodes = futureTask.get(timeout,TimeUnit.MILLISECONDS);
            if (!nativeBarcodes.isEmpty()) {
                decoderCallback.onResult(nativeBarcodes);
            }
        } catch (Exception e) {
        }

    }
}
