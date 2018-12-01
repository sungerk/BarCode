package com.google.android.gms.vision.barcode.internal.client;

import android.annotation.SuppressLint;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SuppressLint("ParcelCreator")
public class BarcodeDetectorOptions implements SafeParcelable {
     /* renamed from: a */
    final int f8193a;
    /* renamed from: b */
    public int f8194b;

    public BarcodeDetectorOptions() {
        this.f8193a = 1;
    }

    public BarcodeDetectorOptions(int i, int i2) {
        this.f8193a = i;
        this.f8194b = i2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
     }
}
