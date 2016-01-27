package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignUpActivity extends AppCompatActivity {

  @Bind(R.id.username) EditText username;
  @Bind(R.id.password) EditText password;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;
  @Bind(R.id.progress_bar) ProgressBar progressBar;

  @Bind(R.id.sign_up) FloatingActionButton signUp;
  @Bind(R.id.scroll_view) ScrollView scrollView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.sign_up)
  public void signUpClicked(final View view) {
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
      showProgressBar();

      ParseUser newUser = new ParseUser();
      newUser.setUsername(username);
      newUser.setPassword(password);
      newUser.setEmail(email);
      newUser.put("phone", phone);
//      newUser.put("photo", new ParseFile("profile.jpg", image));
      newUser.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException exception) {
          hideProgressBar();
          if (exception == null) {
            Toast.makeText(SignUpActivity.this, "Sign up done successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          } else {
            new Builder(SignUpActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
          }
        }
      });
    }
  }

  @OnEditorAction(R.id.phone)
  boolean password(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      signUp.performClick();
      return true;
    }
    return false;
  }

  private void hideProgressBar() {
    setProgressBarIndeterminate(false);
    scrollView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.INVISIBLE);
  }

  private void showProgressBar() {
    setProgressBarIndeterminate(true);
    scrollView.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.VISIBLE);
  }
}
