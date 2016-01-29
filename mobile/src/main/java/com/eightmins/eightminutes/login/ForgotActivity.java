package com.eightmins.eightminutes.login;

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
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ForgotActivity extends AppCompatActivity implements Validator.ValidationListener {
  @Bind(R.id.email) @NotEmpty @Email EditText email;
  @Bind(R.id.forgot) FloatingActionButton forgot;

  private ProgressDialog progress;
  private Validator validator;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot);
    ButterKnife.bind(this);

    validator = new Validator(this);
    validator.setValidationListener(this);
  }

  @OnClick(R.id.forgot)
  public void forgotClicked(final View view) {
    validator.validate();
  }

  @OnEditorAction(R.id.email)
  boolean email(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      forgot.performClick();
      return true;
    }
    return false;
  }

  @Override
  public void onValidationSucceeded() {
    Utils.showProgressBar(this, progress, "Checking Email...");

    ParseUser.requestPasswordResetInBackground(email.getText().toString().trim(), new RequestPasswordResetCallback() {
      public void done(ParseException exception) {
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "Reset Password Email successfully sent.", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(ForgotActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
