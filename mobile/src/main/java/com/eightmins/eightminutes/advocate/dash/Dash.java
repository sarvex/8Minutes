package com.eightmins.eightminutes.advocate.dash;

/**
 * Created by nabhilax on 20/01/16.
 */
public class Dash {
  private String title;
  private String description;

  public Dash(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
