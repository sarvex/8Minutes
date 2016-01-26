package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

  @Bind(R.id.username) EditText username;
  @Bind(R.id.password) EditText password;

  private static final int RC_SIGN_IN = 9001;
  private GoogleSignInOptions googleSignInOptions;
  private GoogleApiClient googleApiClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestId()
        .requestProfile()
        .build();

    googleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
          @Override
          public void onConnectionFailed(ConnectionResult connectionResult) {

          }
        })
        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
        .build();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_login, menu);
    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        result.getSignInAccount().getDisplayName();
      } else {
        // Signed out, show unauthenticated UI.
      }
    }
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


  @OnClick(R.id.button_login_sign_up)
  public void signUp(View view) {
    startActivity(new Intent(this, SignUpActivity.class));
  }

  @OnClick(R.id.button_sign_in)
  public void signIn(View view) {
    String username = this.username.getText().toString().trim();
    String password = this.password.getText().toString().trim();

    if (username.isEmpty()) {
      new AlertDialog.Builder(this).setTitle(R.string.error_title).setMessage(R.string.username_cannot_be_empty)
          .setPositiveButton(string.ok, null).create().show();
    } else if (password.isEmpty()) {
      new AlertDialog.Builder(this).setTitle(R.string.error_title).setMessage(R.string.password_cannot_be_empty)
          .setPositiveButton(string.ok, null).create().show();
    } else {
      setProgressBarIndeterminate(true);
      ParseUser.logInInBackground(username, password, new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException exception) {
          setProgressBarIndeterminate(false);
          if (exception == null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          } else {
            new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage())
                .setPositiveButton(string.ok, null).create().show();
          }
        }
      });
    }
  }

  @OnClick(R.id.facebook_login)
  public void onFacebookLogin(View view) {
    ParseFacebookUtils.logInWithReadPermissionsInBackground(this, null, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException err) {
        if (user == null) {
          Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
        } else if (user.isNew()) {
          Log.d("MyApp", "User signed up and logged in through Facebook!");
        } else {
          Log.d("MyApp", "User logged in through Facebook!");
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
          Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
        } else if (user.isNew()) {
          Log.d("MyApp", "User signed up and logged in through Twitter!");
        } else {
          Log.d("MyApp", "User logged in through Twitter!");
        }
      }
    });
  }

  @OnClick(R.id.google_login)
  public void onGoogleLogin(View view) {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }
}
