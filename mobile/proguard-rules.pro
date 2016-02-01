# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Sarvex\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontobfuscate
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontwarn butterknife.internal.**
-dontwarn com.parse.**
-dontwarn retrofit2.**
-dontwarn retrofit.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.picasso.**
-dontwarn okio.**
-dontwarn rx.**

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes Exceptions
-keepattributes InnerClasses,EnclosingMethod

-keep class retrofit2.** { *; }
-keep class retrofit.** { *; }
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keep class com.parse.** { *; }
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
  native <methods>;
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

-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {
  @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
  @butterknife.* <methods>;
}

-keepclasseswithmembers class * {
  @retrofit.http.* <methods>;
}

# rxjava
-keep class rx.schedulers.Schedulers {
  public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
  public <methods>;
}
-keep class rx.schedulers.TestScheduler {
  public <methods>;
}
-keep class rx.schedulers.Schedulers {
  public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
  long producerIndex;
  long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
  long producerNode;
  long consumerNode;
}

#Share
-keep class android.support.v7.widget.ShareActionProvider { *; }
-keep class android.support.v7.widget.SearchView { *; }

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#youtube
-keep class com.google.android.youtube.player.**  { *; }

-keep class com.mobsandgeeks.saripaar.** {*;}
-keep @com.mobsandgeeks.saripaar.annotation.ValidateUsing class * {*;}

#android-shape-imageview
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.* { *; }
-keepattributes *Annotation,Signature
-dontwarn com.github.siyamed.**
-keep class com.github.siyamed.shapeimageview.**{ *; }

#Iconics
-keep class .R
-keep class **.R$* {
    <fields>;
}
