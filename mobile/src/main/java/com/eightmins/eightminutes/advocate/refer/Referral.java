package com.eightmins.eightminutes.advocate.refer;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by nabhilax on 14/01/16.
 */
@ParseClassName("Referral")
public class Referral extends ParseObject {

  private int image;
  private String name;
  private String description;
  private int status;

  public void load(int image, String name, String description, int status) {
    put("image", image);
    put("name", name);
    put("description", description);
    put("status", status);
  }

  public Referral () {
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

  public String getDescription() {
    return getString("description");
  }

  public void setDescription(String description) {
    put("description", description);
  }

  public int getStatus() {
    return getInt("status");
  }

  public void setStatus(int status) {
    put("status", status);
  }


}
