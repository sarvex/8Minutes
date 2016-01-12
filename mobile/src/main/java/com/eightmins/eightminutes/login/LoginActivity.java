package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.R.layout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

  @Bind(id.username)
  EditText username;
  @Bind(id.password)
  EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    this.setContentView(layout.activity_login);
    ButterKnife.bind(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    this.getMenuInflater().inflate(R.menu.menu_login, menu);
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


  @OnClick(id.button_login_sign_up)
  public void signUp(View view) {
    this.startActivity(new Intent(this, SignUpActivity.class));
  }

  @OnClick(id.button_sign_in)
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
      this.setProgressBarIndeterminate(true);
      ParseUser.logInInBackground(username, password, new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException exception) {
          LoginActivity.this.setProgressBarIndeterminate(false);
          if (exception == null) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          } else {
            new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage())
                .setPositiveButton(string.ok, null).create().show();
          }
        }
      });
    }
  }
}
