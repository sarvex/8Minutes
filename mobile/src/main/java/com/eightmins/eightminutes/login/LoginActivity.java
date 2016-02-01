package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.eightmins.eightminutes.BuildConfig;
import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.utility.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import icepick.Icepick;

public class LoginActivity extends AppCompatActivity {
  private static final int RC_SIGN_IN = 9001;
  @Bind(R.id.username) EditText username;
  @Bind(R.id.password) EditText password;
  @Bind(R.id.facebook_login) FloatingActionButton facebook;
  @Bind(R.id.twitter_login) FloatingActionButton twitter;
  @Bind(R.id.sign_up) FloatingActionButton signUp;
  @Bind(R.id.expand) FloatingActionButton expand;
  @Bind(R.id.login) FloatingActionButton login;
  private ProgressDialog progress;
  private GoogleApiClient googleApiClient;
  private boolean isFabOpen;
  private Animation fabOpen;
  private Animation fabClose;
  private Animation rotateForward;
  private Animation rotateBackward;

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);

    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
    fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
    rotateForward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
    rotateBackward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

    if (BuildConfig.SIGN_UP_ENABLED) {
      expand.setVisibility(View.VISIBLE);
    } else {
      expand.setVisibility(View.GONE);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_login, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
  }

  @OnEditorAction(R.id.password)
  boolean password(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      login.performClick();
      return true;
    }
    return false;
  }

  @OnClick(R.id.login)
  public void login(View view) {
    Utils.showProgressBar(this, progress, getString(R.string.logging_in));
    if (username.getText().toString().contains("@")) {

    }

    ParseUser.logInInBackground(username.getText().toString().trim(),
        password.getText().toString().trim(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException exception) {
            Utils.hideProgressBar(progress);
            if (exception == null) {
              startActivity(new Intent(LoginActivity.this, MainActivity.class)
                  .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            } else {
//            if (user.getBoolean("authenticated")) {

              new Builder(LoginActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
//            } else {
//              Toast.makeText(getApplicationContext(), "Please wait for proper authentication!", Toast.LENGTH_SHORT).show();
//            }
            }
          }
        });
  }

  @OnClick(R.id.forgot)
  public void forgot(View view) {
    startActivity(new Intent(this, ForgotActivity.class));
  }

  @OnClick(R.id.sign_up)
  public void signUp(View view) {
    startActivity(new Intent(this, SignUpActivity.class));
  }

  @OnClick(R.id.expand)
  public void onExpand(View view) {
    Utils.hideKeyboard(this);

    if (isFabOpen) {
      expand.startAnimation(rotateBackward);
      facebook.startAnimation(fabClose);
      twitter.startAnimation(fabClose);
      signUp.startAnimation(fabClose);
    } else {
      expand.startAnimation(rotateForward);
      facebook.startAnimation(fabOpen);
      twitter.startAnimation(fabOpen);
      signUp.startAnimation(fabOpen);
    }

    isFabOpen = !isFabOpen;
    facebook.setClickable(isFabOpen);
    twitter.setClickable(isFabOpen);
    signUp.setClickable(isFabOpen);
  }

  @OnClick(R.id.facebook_login)
  public void onFacebookLogin(View view) {
    List<String> permissions = Arrays.asList("user_photos", "friends_photos", "email", "user_birthday", "user_friends");
    ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        if (user == null) {
          Toast.makeText(getApplicationContext(), "Uh oh. The user cancelled the Facebook login.", Toast.LENGTH_SHORT).show();
        } else if (user.isNew()) {
          Toast.makeText(getApplicationContext(), "User signed up and logged in through Facebook!", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplicationContext(), "User logged in through Facebook!", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  @OnClick(R.id.twitter_login)
  public void onTwitterLogin(View view) {
    ParseTwitterUtils.logIn(this, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        if (user == null) {
          Toast.makeText(getApplicationContext(), "Uh oh. The user cancelled the Twitter login.", Toast.LENGTH_SHORT).show();
        } else if (user.isNew()) {
          Toast.makeText(getApplicationContext(), "User signed up and logged in through Twitter!", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplicationContext(), "User logged in through Twitter!", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
