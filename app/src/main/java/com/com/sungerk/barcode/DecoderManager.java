package com.com.sungerk.barcode;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;
import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public final class DecoderManager implements DecoderCallback {
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler handler;
    private NativeBarcodeDetector nativeBarcodeDetector;



    protected DecoderManager() {
        threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        handler = new Handler(Looper.getMainLooper());
        nativeBarcodeDetector = new NativeBarcodeDetector(new BarcodeDetectorOptions());
    }

    private List<DecodeResultListener> decodeResultListeners = new CopyOnWriteArrayList<>();

    public final void addResultListener(DecodeResultListener decodeResultListener) {
        decodeResultListeners.add(decodeResultListener);
    }

    public final void removeResultListener(DecodeResultListener decodeResultListener) {
        decodeResultListeners.remove(decodeResultListener);
    }


    private final void notifyData(final List<NativeBarcode> result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Iterator<DecodeResultListener> iterator = decodeResultListeners.iterator();
                while (iterator.hasNext()) {
                    DecodeResultListener decodeResultListener = iterator.next();
                    decodeResultListener.onDecodeResult(result);
                }
            }
        });

    }


    public final void decode(int width, int height, int format,byte[] data) {
        CameraSource cameraSource=new CameraSource(width, height, format,data);
        DecoderRunable decoderRunable = new DecoderRunable(nativeBarcodeDetector, cameraSource, this,threadPoolExecutor);
        threadPoolExecutor.execute(decoderRunable);
    }


    @Override
    public void onResult(List<NativeBarcode> nativeBarcodes) {
        notifyData(nativeBarcodes);
    }
}

