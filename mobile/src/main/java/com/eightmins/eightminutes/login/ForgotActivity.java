package com.eightmins.eightminutes.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.utility.Utils;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import icepick.Icepick;

public class ForgotActivity extends AppCompatActivity {
  @Bind(id.email) EditText email;
  @Bind(id.forgot) FloatingActionButton forgot;

  private ProgressDialog progress;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);
    setContentView(R.layout.activity_forgot);
    ButterKnife.bind(this);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @OnClick(id.forgot)
  public void forgotClicked(View view) {
    Utils.showProgressBar(this, progress, "Checking Email...");

    ParseUser.requestPasswordResetInBackground(email.getText().toString().trim(), new RequestPasswordResetCallback() {
      @Override
      public void done(ParseException exception) {
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "Reset Password Email successfully sent.", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finish();
      }
    });
  }

  @OnEditorAction(id.email)
  boolean email(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      forgot.performClick();
      return true;
    }
    return false;
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }
}
