package com.eightmins.eightminutes.advocate.video;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by nabhilax on 23/01/16.
 */
@ParseClassName("Video")
public class Video extends ParseObject {

  public void load(String name, String description, String id) {

    put("name", name);
    put("description", description);
    put("id", id);
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

  public String getId() {
    return getString("id");
  }

  public void setId(String id) {
    put("id", id);
  }

}
