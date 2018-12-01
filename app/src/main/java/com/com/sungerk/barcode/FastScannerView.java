package com.com.sungerk.barcode;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;
import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions;
import me.dm7.barcodescanner.core.BarcodeScannerView;
import me.dm7.barcodescanner.core.DisplayUtils;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class FastScannerView extends BarcodeScannerView {
    private DecoderManager decoderManager;
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
        decoderManager = FastBarcode.getInstance().getDecoderManager();
    }


    @Override
    public void onPreviewFrame(byte[] data, final Camera camera) {
        resetOneShotPreviewCallback(camera);
        if (data == null || data.length == 0) {
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
        decoderManager.decode(width, height,parameters.getPreviewFormat() ,data);
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


}
