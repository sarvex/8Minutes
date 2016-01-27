package com.eightmins.eightminutes.advocate;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.eightmins.eightminutes.MainActivity;
import com.eightmins.eightminutes.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMemberActivity extends AppCompatActivity {

  @Bind(R.id.name) EditText name;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;
  private ProgressDialog progress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_member);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.add_member)
  public void addMemberClicked(final View view) {
    String name = this.name.getText().toString().trim();
    String email = this.email.getText().toString().trim();
    String phone = this.phone.getText().toString().trim();

    if (name.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.username_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (email.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.email_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else if (phone.isEmpty()) {
      new Builder(this).setTitle(R.string.error_title).setMessage(R.string.phone_cannot_be_empty).setPositiveButton(string.ok, null).create().show();
    } else {
      showProgressBar();
      ParseObject member = new ParseObject("Member");
      member.put("name", name);
      member.put("email", email);
      member.put("phone", phone);
      member.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException exception) {
          hideProgressBar();
          if (exception == null) {
            Toast.makeText(AddMemberActivity.this, "Member added sucessfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddMemberActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          } else {
            new Builder(AddMemberActivity.this).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
          }
        }
      });
    }
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
}
