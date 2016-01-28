package com.eightmins.eightminutes.login;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by nabhilax on 28/01/16.
 */
@ParseClassName("_User")
public class User extends ParseUser {
  public void setName(String name) {
    put("name", name);
  }

  public String getName() {
    return getString("name");
  }

  public void setPhone(String phone) {
    put("phone", phone);
  }

  public String getPhone() {
    return getString("phone");
  }

  public void setAuthenticated(boolean authenticated) {
    put("authenticated", authenticated);
  }

  @Override
  public boolean isAuthenticated() {
    return getBoolean("authenticated");
  }
}
