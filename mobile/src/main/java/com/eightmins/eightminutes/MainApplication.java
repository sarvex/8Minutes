package com.eightmins.eightminutes;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.eightmins.eightminutes.advocate.refer.Referral;
import com.eightmins.eightminutes.advocate.video.Video;
import com.eightmins.eightminutes.login.User;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.fabric.sdk.android.Fabric;

/**
 * Created by nabhilax on 12/01/16.
 */
public class MainApplication extends Application {

  private RefWatcher refWatcher;

  public static RefWatcher getRefWatcher(Context context) {
    MainApplication application = (MainApplication) context.getApplicationContext();
    return application.refWatcher;
  }


  @Override
  public void onCreate() {
    super.onCreate();

    ParseObject.registerSubclass(Referral.class);
    ParseObject.registerSubclass(Video.class);
    ParseObject.registerSubclass(User.class);

    Parse.enableLocalDatastore(this);
    Parse.initialize(this, BuildConfig.PARSE_APP_ID, BuildConfig.PARSE_API_KEY);

    ParseFacebookUtils.initialize(this);
    ParseTwitterUtils.initialize(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
    ParseInstallation.getCurrentInstallation().saveInBackground();

    Fabric.with(this, new Crashlytics(), new Answers());

    StrictMode.setThreadPolicy(new Builder().detectAll().penaltyLog().penaltyDeath().build());
    refWatcher = LeakCanary.install(this);

    Iconics.init(this);
    Iconics.registerFont(new GoogleMaterial());
    Iconics.registerFont(new FontAwesome());
  }
}
