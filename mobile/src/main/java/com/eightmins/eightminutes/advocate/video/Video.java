package com.eightmins.eightminutes.advocate.video;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nabhilax on 23/01/16.
 */
public class Video implements Parcelable {

  public static final Creator<Video> CREATOR = new Creator<Video>() {
    @Override
    public Video createFromParcel(Parcel parcel) {
      return new Video(parcel);
    }

    @Override
    public Video[] newArray(int size) {
      return new Video[size];
    }
  };

  private String name;
  private String description;
  private String url;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
  }

  public Video(String name, String description, String url) {

    this.name = name;
    this.description = description;
    this.url = url;
  }

  protected Video(Parcel parcel) {
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
