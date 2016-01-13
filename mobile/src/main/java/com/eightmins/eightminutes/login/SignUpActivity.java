package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog.Builder;
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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

  @Bind(R.id.username) EditText username;
  @Bind(R.id.password) EditText password;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    ButterKnife.bind(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

  @OnClick(R.id.button_login_sign_up)
  public void signUpClicked(View view) {
    String username = this.username.getText().toString().trim();
    String password = this.password.getText().toString().trim();
    String email = this.email.getText().toString().trim();
    String phone = this.phone.getText().toString().trim();

    if (username.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.username_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (password.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.password_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (email.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.email_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (phone.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.phone_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else {
      setProgressBarIndeterminate(true);

      ParseUser newUser = new ParseUser();
      newUser.setUsername(username);
      newUser.setPassword(password);
      newUser.setEmail(email);
      newUser.put("phone", phone);
      newUser.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException exception) {
          setProgressBarIndeterminate(false);
          if (exception == null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          } else {
            new Builder(SignUpActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
          }
        }
      });
    }
  }
}
