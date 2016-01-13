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
-dontwarn butterknife.internal.**
-dontwarn com.parse.**
-dontwarn retrofit2.**

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes Exceptions
-keepattributes InnerClasses,EnclosingMethod

-keep class retrofit2.** { *; }
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keep class com.parse.** { *; }
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
