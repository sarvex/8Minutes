package com.eightmins.eightminutes.advocate.dash;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by nabhilax on 20/01/16.
 */
@ParseClassName("Dash")
public class Dash extends ParseObject {
  private String title;
  private String description;

  public String getTitle() {
    return getString("title");
  }

  public void setTitle(String title) {
    put("title", title);
  }

  public String getDescription() {
    return getString("description");
  }

  public void setDescription(String description) {
    put("description", description);
  }
}
