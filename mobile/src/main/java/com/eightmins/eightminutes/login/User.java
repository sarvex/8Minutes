package com.eightmins.eightminutes.login;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by nabhilax on 28/01/16.
 */
@ParseClassName("_User")
public class User extends ParseUser {
  public int getImage() {
    return getInt("image");
  }

  public void setImage(int image) {
    put("image", image);
  }

  public void setName(String name) {
    put("name", name);
  }

  public String getName() {
    return getString("name");
  }

  public void setOwner(String owner) {
    put("owner", owner);
  }

  public String getOwner() {
    return getString("owner");
  }

  public void setPhone(String phone) {
    put("phone", phone);
  }

  public String getPhone() {
    return getString("phone");
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
    return getInt("earnings");
  }

  public void setEarnings(long earnings) {
    put("earnings", earnings);
  }

  public void setVerified(boolean authenticated) {
    put("verified", authenticated);
  }

  public boolean isVerified() {
    return getBoolean("verified");
  }
}
