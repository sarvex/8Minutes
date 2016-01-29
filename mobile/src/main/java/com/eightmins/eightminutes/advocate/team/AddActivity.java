package com.eightmins.eightminutes.advocate.team;

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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class AddActivity extends AppCompatActivity implements Validator.ValidationListener {

  @Bind(R.id.name) @NotEmpty EditText name;
  @Bind(R.id.email) @NotEmpty @Email EditText email;
  @Bind(R.id.phone) @NotEmpty EditText phone;
  @Bind(R.id.add_member) FloatingActionButton addMember;

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

  @OnEditorAction(R.id.phone)
  boolean password(int actionId) {
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
    progress.setMessage("Adding Member...");
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  @Override
  public void onValidationSucceeded() {
    showProgressBar();
    ParseObject member = new ParseObject("Member");
    member.put("name", name.getText().toString().trim());
    member.put("email", email.getText().toString().trim());
    member.put("phone", phone.getText().toString().trim());
    member.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException exception) {
        hideProgressBar();
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "Member added sucessfully", Toast.LENGTH_SHORT).show();
          startActivity(new Intent(AddActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        } else {
          new Builder(AddActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
        }
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
