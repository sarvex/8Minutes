package com.eightmins.eightminutes.login;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SignUpActivity extends AppCompatActivity implements Validator.ValidationListener {
  @Bind(R.id.name) @NotEmpty EditText name;
  @Bind(R.id.username) @NotEmpty EditText username;
  @Bind(R.id.password) @Password(scheme = Password.Scheme.ALPHA_NUMERIC, message = "Password should be more than 6 alphanumeric characters") EditText password;
  @Bind(R.id.confirm) @ConfirmPassword EditText confirm;
  @Bind(R.id.email) @NotEmpty @Email EditText email;
  @Bind(R.id.phone) @NotEmpty EditText phone;
  @Bind(R.id.sign_up) FloatingActionButton signUp;

  private ProgressDialog progress;
  private Validator validator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    ButterKnife.bind(this);

    validator = new Validator(this);
    validator.setValidationListener(this);
  }

  @OnClick(R.id.sign_up)
  public void signUpClicked(final View view) {
    validator.validate();
  }

  @OnEditorAction(R.id.phone)
  boolean password(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      signUp.performClick();
      return true;
    }
    return false;
  }

  @Override
  public void onValidationSucceeded() {
    Utils.hideKeyboard(this);
    Utils.showProgressBar(this, progress, "Signing Up...");

    User user = new User();
    user.setUsername(username.getText().toString().trim());
    user.setPassword(password.getText().toString().trim());
    user.setEmail(email.getText().toString().trim());
    user.setPhone(phone.getText().toString().trim());
    user.setName(name.getText().toString().trim());
    user.setVerified(false);
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

  @Override
  public void onValidationFailed(List<ValidationError> errors) {
    for (ValidationError error : errors) {
      View view = error.getView();
      String message = error.getCollatedErrorMessage(this);

      // Display error messages ;)
      if (view instanceof EditText) {
        ((EditText) view).setError(message);
      } else {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
      }
    }
  }
}
