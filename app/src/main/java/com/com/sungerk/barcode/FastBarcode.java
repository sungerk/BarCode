package com.com.sungerk.barcode;

import android.annotation.SuppressLint;
import android.content.Context;

public final class FastBarcode {

    private static final String SO_FILE_NAME = "libfastbar.so";

    private static volatile FastBarcode instance;
    private DecoderManager decoderManager;


    public static FastBarcode getInstance() {
        if (instance == null) {
            synchronized (FastBarcode.class) {
                if (instance == null) {
                    instance = new FastBarcode();
                }
            }
        }
        return instance;
    }

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    public void initialize(Context context) {
        try {
            String fastScanSoFilePath = MyUtils.findNativeLibraryPath(context, SO_FILE_NAME);
            System.load(fastScanSoFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FastBarcode() {
        decoderManager=new DecoderManager();
    }


    public DecoderManager getDecoderManager() {
        return  decoderManager;

    }


}
