package com.eightmins.eightminutes.advocate.team;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by nabhilax on 14/01/16.
 */
@ParseClassName("Member")
public class Member extends ParseUser {

  public void load(int image, String name, int members, int installed, int progress, int pending, long earnings) {
    put("image", image);
    put("name", name);
    put("members", members);
    put("installed", installed);
    put("progress", progress);
    put("pending", pending);
    put("earnings", earnings);
  }

  public int getImage() {
    return getInt("image");
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
}
