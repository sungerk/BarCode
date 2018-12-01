package com.com.sungerk.barcode;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;
import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import me.dm7.barcodescanner.core.BarcodeScannerView;
import me.dm7.barcodescanner.core.DisplayUtils;

import java.util.Arrays;
import java.util.List;

public class FastScannerView extends BarcodeScannerView {
    private NativeBarcodeDetector nativeBarcodeDetector;
    private Handler handler;
    private ResultHandler mResultHandler;

    private boolean isCameraOpen;

    public FastScannerView(Context context) {
        super(context);
        init();
    }

    public FastScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        handler = new Handler(Looper.getMainLooper());
        nativeBarcodeDetector = new NativeBarcodeDetector(new BarcodeDetectorOptions());
    }


    @Override
    public void onPreviewFrame(byte[] data, final Camera camera) {
        if (data == null || data.length == 0 || mResultHandler == null) {
            resetOneShotPreviewCallback(camera);
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        int width = size.width;
        int height = size.height;
        if (DisplayUtils.getScreenOrientation(getContext()) == Configuration.ORIENTATION_PORTRAIT) {
            int rotationCount = getRotationCount();
            if (rotationCount == 1 || rotationCount == 3) {
                int tmp = width;
                width = height;
                height = tmp;
            }
            data = getRotatedData(data, camera);
        }
        final NativeBarcode[] result = nativeBarcodeDetector.decode(width, height, data);
        if (result == null || result.length == 0) {
            resetOneShotPreviewCallback(camera);
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                mResultHandler.handleResult(Arrays.asList(result));
                resetOneShotPreviewCallback(camera);
            }
        });

    }

    public void setResultHandler(ResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    public void startCamera() {
        super.startCamera();
        isCameraOpen = true;
    }

    public void stopCamera() {
        super.stopCamera();
        isCameraOpen = false;
    }

    private void resetOneShotPreviewCallback(Camera camera) {
        if (camera == null || !isCameraOpen) {
            return;
        }
        try {
            camera.setOneShotPreviewCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface ResultHandler {
        void handleResult(List<NativeBarcode> rawResult);
    }
}
