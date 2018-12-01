package com.com.sungerk.barcode;

import android.graphics.*;
import android.support.annotation.Nullable;


import java.io.ByteArrayOutputStream;

/**
 * Utils functions for bitmap conversions.
 */
public class BitmapUtils {


    @Nullable
    public static Bitmap getGrayBitMap(int width, int height, int format ,byte[] data) {
        Bitmap bitmap = getBitmap(width, height,format, data);
        if (bitmap == null) {
            return null;
        }
        return readGrayBitMap(bitmap);
    }

      public static Bitmap getBitmap(int width, int height, int format,byte[] data) {
        YuvImage yuv = new YuvImage(data, format, width, height, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 80, out);
        byte[] bytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

     }

    private static Bitmap readGrayBitMap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap grayImg = null;
        try {
            grayImg = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(grayImg);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();//仰仗这玩意了
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                    colorMatrix);
            paint.setColorFilter(colorMatrixFilter);
            canvas.drawBitmap(bitmap, 0, 0, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grayImg;
    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int rotation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

}
