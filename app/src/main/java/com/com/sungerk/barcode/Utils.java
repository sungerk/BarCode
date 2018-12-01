package com.com.sungerk.barcode;

import android.content.Context;

public class Utils {

    public static String findNativeLibraryPath(Context context, String libraryName) {
        String dataDir = context.getApplicationContext().getApplicationInfo().dataDir;
        return dataDir + "/lib/" + libraryName;
    }

}
