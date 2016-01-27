package com.eightmins.eightminutes.advocate;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReferralActivity extends AppCompatActivity {

  @Bind(R.id.name) EditText name;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;
  @Bind(R.id.address1) EditText address1;
  @Bind(R.id.address2) EditText address2;
  @Bind(R.id.locality) EditText locality;
  @Bind(R.id.city) EditText city;
  @Bind(R.id.pincode) EditText pincode;
  @Bind(R.id.average_bill) EditText averageBill;
  @Bind(R.id.notes) EditText notes;
  @Bind(R.id.progress_bar) ProgressBar progressBar;
  @Bind(R.id.scroll_view) ScrollView scrollView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_referral);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.refer)
  public void onReferClicked(final View view) {
    String name = this.name.getText().toString().trim();
    String email = this.email.getText().toString().trim();
    String phone = this.phone.getText().toString().trim();
    String address1 = this.address1.getText().toString().trim();
    String address2 = this.address2.getText().toString().trim();
    String locality = this.locality.getText().toString().trim();
    String city = this.city.getText().toString().trim();
    String pincode = this.pincode.getText().toString().trim();
    String averageBill = this.averageBill.getText().toString().trim();
    String notes = this.notes.getText().toString().trim();

    if (name.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.name_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (email.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.email_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (phone.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.phone_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (address1.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.address1_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (address2.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.address2_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (locality.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.locality_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (city.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.city_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (pincode.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.pincode_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (averageBill.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.average_bill_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (notes.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.notes_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else {
      setProgressBarIndeterminate(true);

      ParseObject referral = new ParseObject("Referral");
      referral.put("name", name);
      referral.put("email", email);
      referral.put("phone", phone);
      referral.put("address1", address1);
      referral.put("address2", address2);
      referral.put("locality", locality);
      referral.put("city", city);
      referral.put("pincode", pincode);
      referral.put("averageBill", averageBill);
      referral.put("notes", notes);
      referral.put("status", "referred");
      referral.put("lead", ParseUser.getCurrentUser().getUsername());

      hideProgressBar();
      referral.saveInBackground(new SaveCallback() {
        public void done(ParseException exception) {
          showProgressBar();
          if (exception == null) {
            Toast.makeText(AddReferralActivity.this, "Referral done successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddReferralActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          } else {
            new Builder(AddReferralActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
          }
      }});
    }
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
