package com.sungerk.example;

import android.app.Application;
import com.sungerk.barcode.FastBarcode;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FastBarcode.getInstance().initialize(this);

    }


}
