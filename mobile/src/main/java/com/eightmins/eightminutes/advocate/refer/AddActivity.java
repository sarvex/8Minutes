package com.eightmins.eightminutes.advocate.refer;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class AddActivity extends AppCompatActivity implements Validator.ValidationListener {
  protected int result = RESULT_OK;

  @Bind(R.id.name) @NotEmpty EditText name;
  @Bind(R.id.organization) EditText organization;
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
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

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
    progress.setMessage(getString(R.string.referring));
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  @Override
  public void onValidationSucceeded() {
    hideProgressBar();

    Referral referral = new Referral();
    referral.setName(name.getText().toString().trim());
    referral.setOrganization(organization.getText().toString().trim());
    referral.setEmail(email.getText().toString().trim());
    referral.setPhone(phone.getText().toString().trim());
    referral.setAddress1(address1.getText().toString().trim());
    referral.setAddress2(address2.getText().toString().trim());
    referral.setLocality(locality.getText().toString().trim());
    referral.setCity(city.getText().toString().trim());
    referral.setPincode(pincode.getText().toString().trim());
    referral.setAverageBill(averageBill.getText().toString().trim());
    referral.setNotes(notes.getText().toString().trim());
    referral.setStatus("referred");
    referral.setLead((User) ParseUser.getCurrentUser());

    referral.saveInBackground(new SaveCallback() {
      public void done(ParseException exception) {
        showProgressBar();
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "Referral done successfully", Toast.LENGTH_SHORT).show();
          finish();
        } else {
          new Builder(AddActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage())
              .setPositiveButton(android.R.string.ok, new OnClickListener() {
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
    super.finish();
  }

  @Override
  public void onValidationFailed(List<ValidationError> errors) {
    for (ValidationError error : errors) {
      View view = error.getView();
      String message = error.getCollatedErrorMessage(this);

      if (view instanceof EditText) {
        ((EditText) view).setError(message);
      } else {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
      }
    }
  }
}
