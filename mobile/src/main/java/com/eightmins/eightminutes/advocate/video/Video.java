package com.eightmins.eightminutes.advocate.video;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by nabhilax on 23/01/16.
 */
@ParseClassName("Video")
public class Video extends ParseObject {

  public String getTitle() {
    return getString("title");
  }

  public void setTitle(String name) {
    put("title", name);
  }

  public String getDescription() {
    return getString("description");
  }

  public void setDescription(String description) {
    put("description", description);
  }

  public String getUrl() {
    return getString("url");
  }

  public void setUrl(String id) {
    put("url", id);
  }

}
