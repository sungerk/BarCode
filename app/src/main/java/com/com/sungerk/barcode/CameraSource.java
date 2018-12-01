package com.com.sungerk.barcode;

public class CameraSource {
    public int width;
    public int height;
    public   int format;
    public byte[] data;

    public CameraSource(int width, int height, int format,byte[] data) {
        this.width = width;
        this.height = height;
        this.format=format;
        this.data = data;
    }
}
