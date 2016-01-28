package com.eightmins.eightminutes.advocate.refer;

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
import com.parse.ParseUser;
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
  @Bind(R.id.address1) @NotEmpty EditText address1;
  @Bind(R.id.address2) @NotEmpty EditText address2;
  @Bind(R.id.locality) @NotEmpty EditText locality;
  @Bind(R.id.city) @NotEmpty EditText city;
  @Bind(R.id.pincode) @NotEmpty EditText pincode;
  @Bind(R.id.average_bill) @NotEmpty EditText averageBill;
  @Bind(R.id.notes) EditText notes;
  @Bind(R.id.refer) FloatingActionButton refer;

  private ProgressDialog progress;
  private Validator validator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_referral_add);
    ButterKnife.bind(this);

    validator = new Validator(this);
    validator.setValidationListener(this);
  }

  @OnEditorAction(R.id.notes)
  boolean password(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      refer.performClick();
      return true;
    }
    return false;
  }

  @OnClick(R.id.refer)
  public void onReferClicked(final View view) {
    validator.validate();
  }

  private void hideProgressBar() {
    if ((progress != null) && progress.isShowing()) {
      progress.dismiss();
    }
  }

  private void showProgressBar() {
    progress = new ProgressDialog(this);
    progress.setMessage("Referring...");
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  @Override
  public void onValidationSucceeded() {
    hideProgressBar();

    ParseObject referral = new ParseObject("Referral");
    referral.put("name", name.getText().toString().trim());
    referral.put("email", email.getText().toString().trim());
    referral.put("phone", phone.getText().toString().trim());
    referral.put("address1", address1.getText().toString().trim());
    referral.put("address2", address2.getText().toString().trim());
    referral.put("locality", locality.getText().toString().trim());
    referral.put("city", city.getText().toString().trim());
    referral.put("pincode", pincode.getText().toString().trim());
    referral.put("averageBill", averageBill.getText().toString().trim());
    referral.put("notes", notes.getText().toString().trim());
    referral.put("status", "referred");
    referral.put("lead", ParseUser.getCurrentUser().getUsername());

    referral.saveInBackground(new SaveCallback() {
      public void done(ParseException exception) {
        showProgressBar();
        if (exception == null) {
          Toast.makeText(AddActivity.this, "Referral done successfully", Toast.LENGTH_SHORT).show();
          startActivity(new Intent(AddActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        } else {
          new Builder(AddActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(android.R.string.ok, null).create().show();
        }
      }
    });
  }

  @Override
  public void onValidationFailed(List<ValidationError> errors) {
    for (ValidationError error : errors) {
      View view = error.getView();
      String message = error.getCollatedErrorMessage(this);

      if (view instanceof EditText) {
        ((EditText) view).setError(message);
      } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
      }
    }
  }
}
