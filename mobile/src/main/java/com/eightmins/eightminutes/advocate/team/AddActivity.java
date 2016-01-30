package com.eightmins.eightminutes.advocate.team;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.login.User;
import com.eightmins.eightminutes.utility.Utils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class AddActivity extends AppCompatActivity implements Validator.ValidationListener {

  @Bind(R.id.name) @NotEmpty EditText name;
  @Bind(R.id.email) @NotEmpty @Email EditText email;
  @Bind(R.id.phone) @NotEmpty EditText phone;
  @Bind(R.id.username) @NotEmpty EditText username;
  @Bind(R.id.add_member) FloatingActionButton addMember;

  protected int result = RESULT_OK;
  private ProgressDialog progress;
  private Validator validator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_member_add);
    ButterKnife.bind(this);

    validator = new Validator(this);
    validator.setValidationListener(this);
  }

  @OnEditorAction(R.id.username)
  boolean editorAction(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      addMember.performClick();
      return true;
    }
    return false;
  }

  @OnClick(R.id.add_member)
  public void addMemberClicked(final View view) {
    validator.validate();
  }

  private void hideProgressBar() {
    if ((progress != null) && progress.isShowing()) {
      progress.dismiss();
    }
  }

  private void showProgressBar() {
    progress = new ProgressDialog(this);
    progress.setMessage(getString(R.string.adding_member));
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  @Override
  public void onValidationSucceeded() {
    showProgressBar();
    final String token = ParseUser.getCurrentUser().getSessionToken();
    User user = new User();
    user.setDefaults();
    user.setUsername(username.getText().toString().trim());
    user.setEmail(email.getText().toString().trim());
    user.setPhone(phone.getText().toString().trim());
    user.setName(name.getText().toString().trim());
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException exception) {
        hideProgressBar();
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_SHORT).show();
          try {
            ParseUser.become(token);
          } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
          finish();
        } else {
          new Builder(AddActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage())
              .setPositiveButton(string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  result = RESULT_CANCELED;
                  finish();
                }
              }).create().show();
        }
      }
    });
  }

  @Override
  public void finish() {
    Utils.hideKeyboard(this);
    setResult(result);
    if (result == RESULT_OK) {
      ParseUser.getCurrentUser().increment("members");
      ParseUser.getCurrentUser().saveEventually();
    }
    super.finish();
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
