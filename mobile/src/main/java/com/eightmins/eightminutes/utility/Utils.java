package com.eightmins.eightminutes.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Sarvex on 1/27/2016.
 */
public class Utils {

  public static void hideProgressBar(ProgressDialog progress) {
    if ((progress != null) && progress.isShowing()) {
      progress.dismiss();
    }
  }

  public static void showProgressBar(Activity activity, ProgressDialog progress, String message) {
    progress = new ProgressDialog(activity);
    progress.setMessage(message);
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  public static void hideKeyboard(Activity activity) {
    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
  }
}
