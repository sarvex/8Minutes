package com.eightmins.eightminutes.advocate.refer;

import android.R.string;
import android.app.Activity;
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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import icepick.Icepick;

public class AddActivity extends AppCompatActivity {
  protected int result = Activity.RESULT_OK;

  @Bind(R.id.name) EditText name;
  @Bind(R.id.organization) EditText organization;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;
  @Bind(R.id.address1) EditText address1;
  @Bind(R.id.address2) EditText address2;
  @Bind(R.id.locality) EditText locality;
  @Bind(R.id.city) EditText city;
  @Bind(R.id.pincode) EditText pincode;
  @Bind(R.id.average_bill) EditText averageBill;
  @Bind(R.id.notes) EditText notes;
  @Bind(R.id.refer) FloatingActionButton refer;

  private ProgressDialog progress;

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);

    setContentView(R.layout.activity_referral_add);
    ButterKnife.bind(this);
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
  public void onReferClicked(View view) {
    Utils.hideKeyboard(this);

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
      @Override
      public void done(ParseException exception) {
        Utils.showProgressBar(getParent(), progress, getString(R.string.referring));
        if (exception == null) {
          Toast.makeText(getApplicationContext(), "Referral done successfully", Toast.LENGTH_SHORT).show();
          finish();
        } else {
          new Builder(AddActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage())
              .setPositiveButton(string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  result = Activity.RESULT_CANCELED;
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
}
