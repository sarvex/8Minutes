package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.utility.Utils;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignUpActivity extends AppCompatActivity {
  @Bind(R.id.name) EditText name;
  @Bind(R.id.username) EditText username;
  @Bind(R.id.password) EditText password;
  @Bind(R.id.confirm) EditText confirm;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;
  @Bind(R.id.sign_up) FloatingActionButton signUp;

  private ProgressDialog progress;

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.sign_up)
  public void signUpClicked(View view) {
    Utils.hideKeyboard(this);
    Utils.showProgressBar(this, progress, "Signing Up...");

    User user = new User();
    user.setSuperDefaults();
    user.setUsername(username.getText().toString().trim());
    user.setPassword(password.getText().toString().trim());
    user.setEmail(email.getText().toString().trim());
    user.setPhone(phone.getText().toString().trim());
    user.setName(name.getText().toString().trim());
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException exception) {
        Utils.hideProgressBar(progress);
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "Sign up done successfully", Toast.LENGTH_SHORT).show();
        } else {
          new Builder(SignUpActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
        }
        startActivity(new Intent(SignUpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
      }
    });
  }

  @OnEditorAction(R.id.phone)
  boolean password(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      signUp.performClick();
      return true;
    }
    return false;
  }
}
