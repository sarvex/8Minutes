package com.eightmins.eightminutes.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eightmins.eightminutes.R;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

  @OnClick(R.id.link_facebook)
  public void linkFacebook(View view) {
    final ParseUser user = ParseUser.getCurrentUser();
    if (!ParseFacebookUtils.isLinked(user)) {
      ParseFacebookUtils.linkWithReadPermissionsInBackground(user, this, null, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ParseFacebookUtils.isLinked(user)) {
            Toast.makeText(ProfileActivity.this, "Woohoo, user logged in with Facebook!", Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else {
      ParseFacebookUtils.unlinkInBackground(user, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ex == null) {
            Toast.makeText(ProfileActivity.this, "The user is no longer associated with their Facebook account.", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  @OnClick(R.id.link_twitter)
  public void linkTwitter(View view) {
    final ParseUser user = ParseUser.getCurrentUser();
    if (!ParseTwitterUtils.isLinked(user)) {
      ParseTwitterUtils.link(user, this, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ParseTwitterUtils.isLinked(user)) {
            Toast.makeText(ProfileActivity.this, "Woohoo, user logged in with Twitter!", Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else {
      ParseTwitterUtils.unlinkInBackground(user, new SaveCallback() {
        @Override
        public void done(ParseException ex) {
          if (ex == null) {
            Toast.makeText(ProfileActivity.this, "The user is no longer associated with their Twitter account.", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
  }
}
