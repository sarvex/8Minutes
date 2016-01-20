package com.eightmins.eightminutes.advocate.dash;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nabhilax on 20/01/16.
 */
public class Dash implements Parcelable {

  public static final Creator<Dash> CREATOR = new Creator<Dash>() {
    @Override
    public Dash createFromParcel(Parcel parcel) {
      return new Dash(parcel);
    }

    @Override
    public Dash[] newArray(int size) {
      return new Dash[size];
    }
  };

  private String title;
  private String description;

  protected Dash(Parcel parcel) {
    title = parcel.readString();
    description = parcel.readString();
  }

  public Dash(String title, String description) {
    this.title = title;
    this.description = description;
  }

  @Override
  public int describeContents() {
    return 0;
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

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(description);
  }
}
