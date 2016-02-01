package com.eightmins.eightminutes.login;

import com.eightmins.eightminutes.R;
import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by nabhilax on 28/01/16.
 */
@ParseClassName("_User")
public class User extends ParseUser {
  public void setDefaults () {
    setSuperDefaults();
    setOwner((User) ParseUser.getCurrentUser());
  }

  public void setSuperDefaults() {
    setPassword("Password123");
    setMembers(0);
    setInstalled(0);
    setPending(0);
    setProgress(0);
    setEarnings(0L);
    setVerified(false);
  }

  public int getImage() {
    int image = getInt("image");
    if (image == 0) {
      image = R.mipmap.ic_account_circle;
    }
    return image;
  }

  public void setImage(int image) {
    put("image", image);
  }

  public String getName() {
    return getString("name");
  }

  public void setName(String name) {
    put("name", name);
  }

  public User getOwner() {
    return (User)get("owner");
  }

  public void setOwner(User owner) {
    put("owner", owner);
  }

  public String getPhone() {
    return getString("phone");
  }

  public void setPhone(String phone) {
    put("phone", phone);
  }

  public int getMembers() {
    return getInt("members");
  }

  public void setMembers(int members) {
    put("members", members);
  }

  public int getInstalled() {
    return getInt("installed");
  }

  public void setInstalled(int installed) {
    put("installed", installed);
  }

  public int getProgress() {
    return getInt("progress");
  }

  public void setProgress(int progress) {
    put("progress", progress);
  }

  public int getPending() {
    return getInt("pending");
  }

  public void setPending(int pending) {
    put("pending", pending);
  }

  public long getEarnings() {
    return getLong("earnings");
  }

  public void setEarnings(long earnings) {
    put("earnings", earnings);
  }

  public boolean isVerified() {
    return getBoolean("verified");
  }

  public void setVerified(boolean verified) {
    put("verified", verified);
  }
}
