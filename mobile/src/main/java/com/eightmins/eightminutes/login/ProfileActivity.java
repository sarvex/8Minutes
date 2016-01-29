package com.eightmins.eightminutes.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eightmins.eightminutes.R;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {
  @Bind(R.id.username) EditText username;
  @Bind(R.id.name) EditText name;
  @Bind(R.id.password) EditText password;
  @Bind(R.id.email) EditText email;
  @Bind(R.id.phone) EditText phone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    ButterKnife.bind(this);

    ParseUser user = ParseUser.getCurrentUser();
    if (user != null) {
      username.setText(user.getUsername());
      name.setText(String.valueOf(user.get("name")));
      password.setText(user.getSessionToken());
      email.setText(user.getEmail());
      phone.setText(String.valueOf(user.get("phone")));
    }
  }


  @OnClick(R.id.link_facebook)
  public void linkFacebook(View view) {
    final ParseUser user = ParseUser.getCurrentUser();
    if (ParseFacebookUtils.isLinked(user)) {
      ParseFacebookUtils.unlinkInBackground(user, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ex == null) {
            Toast.makeText(getApplicationContext(), "The user is no longer associated with their Facebook account.", Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else {
      ParseFacebookUtils.linkWithReadPermissionsInBackground(user, this, null, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ParseFacebookUtils.isLinked(user)) {
            Toast.makeText(getApplicationContext(), "Woohoo, user logged in with Facebook!", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  @OnClick(R.id.link_twitter)
  public void linkTwitter(View view) {
    final ParseUser user = ParseUser.getCurrentUser();
    if (ParseTwitterUtils.isLinked(user)) {
      ParseTwitterUtils.unlinkInBackground(user, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ex == null) {
            Toast.makeText(getApplicationContext(), "The user is no longer associated with their Twitter account.", Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else {
      ParseTwitterUtils.link(user, this, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ParseTwitterUtils.isLinked(user)) {
            Toast.makeText(getApplicationContext(), "Woohoo, user logged in with Twitter!", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }
}
