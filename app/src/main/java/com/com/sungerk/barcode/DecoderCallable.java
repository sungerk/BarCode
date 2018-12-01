package com.com.sungerk.barcode;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.android.gms.vision.barcode.internal.NativeBarcode;
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

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
            Mat img = new Mat(cameraSource.height, cameraSource.width, CvType.CV_8UC1);
            Mat gray = new Mat(cameraSource.height, cameraSource.width, CvType.CV_8UC1);
            img.put(0, 0, cameraSource.data);
            Imgproc.cvtColor(img, gray, Imgproc.COLOR_YUV420sp2GRAY);
            Bitmap bitmap = OpencvUtil.matToBitmap(gray);

            NativeBarcode[] result = nativeBarcodeDetector.decode(bitmap);
            if (MyUtils.isNotNull(result)) {
                return Arrays.asList(result);
            }

//            for (int i = 0; i < 5; i++) {
//                Mat rotateMat = OpencvUtil.rotate(gray, i * 8);
//                Bitmap bitmap= OpencvUtil.matToBitmap(rotateMat);
//                if (bitmap==null){
//                    break;
//                }
//                NativeBarcode[] result = nativeBarcodeDetector.decode(bitmap);
//                if (MyUtils.isNotNull(result)) {
//                    return Arrays.asList(result);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release();
        }
        return Collections.emptyList();

    }
}
