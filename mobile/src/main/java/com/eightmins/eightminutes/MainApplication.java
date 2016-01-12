package com.eightmins.eightminutes;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.parse.Parse;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

/**
 * Created by nabhilax on 12/01/16.
 */
public class MainApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Parse.enableLocalDatastore(this);
    Parse.initialize(this);
    Fabric.with(this, new Crashlytics(), new Answers());
    LeakCanary.install(this);
  }
}
