package com.sungerk.barcode;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DecoderManager implements DecoderCallback {
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler handler;
    private NativeBarcodeDetector nativeBarcodeDetector;


    protected DecoderManager() {
        threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        handler = new Handler(Looper.getMainLooper());
        nativeBarcodeDetector = new NativeBarcodeDetector();
    }

    public void setFormats(List<Integer> formats) {
        //位或运算
        if (formats == null || formats.isEmpty()) {
            return;
        }
        int barcodeFormats = formats.get(0);
        for (int i = 0; i < formats.size()-1; i++) {
            barcodeFormats = barcodeFormats | formats.get(i+1);
        }
        nativeBarcodeDetector.setSupportFormats(barcodeFormats);
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


    public final void decode(int width, int height, int format, byte[] data) {
        CameraSource cameraSource = new CameraSource(width, height, format, data);
        DecoderRunable decoderRunable = new DecoderRunable(nativeBarcodeDetector, cameraSource, this, threadPoolExecutor);
        threadPoolExecutor.execute(decoderRunable);
    }


    @Override
    public void onResult(List<NativeBarcode> nativeBarcodes) {
        notifyData(nativeBarcodes);
    }
}

