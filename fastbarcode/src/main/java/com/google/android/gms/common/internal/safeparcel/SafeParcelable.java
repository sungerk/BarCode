//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcelable;

public interface SafeParcelable extends Parcelable {
    String NULL = "SAFE_PARCELABLE_NULL_STRING";

    public @interface Reserved {
        int[] value();
    }

    public @interface Param {
        int id();
    }

    public @interface Constructor {
    }

    public @interface Indicator {
        String getter() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface VersionField {
        int id();

        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface Field {
        int id();

        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        String type() default "SAFE_PARCELABLE_NULL_STRING";

        String defaultValue() default "SAFE_PARCELABLE_NULL_STRING";

        String defaultValueUnchecked() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface Class {
        String creator();

        boolean validate() default false;
    }
}
