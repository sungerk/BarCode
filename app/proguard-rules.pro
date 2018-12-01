#混淆基础配置文件

#指定代码的压缩级别
-optimizationpasses 5
 #包明不混合大小写
-dontusemixedcaseclassnames
 #不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
  #优化  不优化输入的类文件
-dontoptimize
  #不做预校验
-dontpreverify
  #混淆时是否记录日志
-verbose
  # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
  #忽略警告
-ignorewarning
#apk 包内所有 class 的内部结构
-dump class_files.txt
  #未混淆的类和成员
-printseeds seeds.txt
  #列出从 apk 中删除的代码
-printusage unused.txt
  #混淆前后的映射
-printmapping mapping.txt
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
-keepattributes SourceFile,LineNumberTable
-dontwarn javax.annotation.**
-dontoptimize

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
#1.support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}

-dontwarn java.lang.invoke.*


# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}


-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

-keep class **.R$* {  *; }

-keep class me.dm7.barcodescanner.{*;}
-keep interface me.dm7.barcodescanner.{*;}
-keep class me.dm7.barcodescanner.** { *; }




#firebase google
-dontwarn com.google.firebase.components.Component$Instantiation
# Required by multiple libraries which have native code
-keep @interface com.google.android.apps.common.proguard.UsedByNative
-keepnames @com.google.android.apps.common.proguard.UsedByNative class *
-keepclassmembernames class * {
  @com.google.android.apps.common.proguard.UsedByNative *;
}
-keepclassmembers class * extends com.google.android.gms.internal.firebase_ml.zzmd {
  <fields>;
}
-keepclassmembers class * extends com.google.android.gms.internal.firebase_ml.zzmd {
  <fields>;
}
-keepclassmembers class * {
  @com.google.android.gms.internal.firebase_ml.zzdc <fields>;
}
-keepclassmembers class com.google.android.gms.common.api.internal.BasePendingResult {
  com.google.android.gms.common.api.internal.BasePendingResult$ReleasableResultGuardian mResultGuardian;
}
# Proguard flags for consumers of the Google Play services SDK
# https://developers.google.com/android/guides/setup#add_google_play_services_to_your_project

# Keep SafeParcelable NULL value, needed for reflection by DowngradeableSafeParcel
-keepclassmembers public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# Needed for Parcelable/SafeParcelable classes & their creators to not get renamed, as they are
# found via reflection.
-keep class com.google.android.gms.common.internal.ReflectedParcelable
-keepnames class * implements com.google.android.gms.common.internal.ReflectedParcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}

# Keep the classes/members we need for client functionality.
-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}

# Keep the names of classes/members we need for client functionality.
-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}

# Keep Dynamite API entry points
-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep @com.google.android.gms.common.util.DynamiteApi public class * {
  public <fields>;
  public <methods>;
}

# Needed when building against pre-Marshmallow SDK.
-dontwarn android.security.NetworkSecurityPolicy

# Needed when building against Marshmallow SDK.
-dontwarn android.app.Notification

# Protobuf has references not on the Android boot classpath
-dontwarn sun.misc.Unsafe
-dontwarn libcore.io.Memory

# Internal Google annotations for generating Proguard keep rules.
-dontwarn com.google.android.apps.common.proguard.UsedBy*

# Annotations referenced by the SDK but whose definitions are contained in
# non-required dependencies.
-dontwarn javax.annotation.**
-dontwarn org.checkerframework.**
-keepclassmembers class * extends com.google.android.gms.internal.clearcut.zzcg {
  <fields>;
}
-keepclassmembers class * extends com.google.android.gms.internal.vision.zzfy {
  <fields>;
}
