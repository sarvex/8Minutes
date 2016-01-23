package com.eightmins.eightminutes.advocate.refer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nabhilax on 14/01/16.
 */
public class Referral implements Parcelable{

  public static final Creator<Referral> CREATOR = new Creator<Referral>() {
    @Override
    public Referral createFromParcel(Parcel parcel) {
      return new Referral(parcel);
    }

    @Override
    public Referral[] newArray(int size) {
      return new Referral[size];
    }
  };

  private int image;
  private String name;
  private String description;
  private int status;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }

  public Referral(int image, String name, String description, int status) {
    this.image = image;
    this.name = name;
    this.description = description;
    this.status = status;

  }

  protected Referral(Parcel parcel) {
  }

  public Referral () {

  }

  public int getImage() {
    return image;
  }

  public void setImage(int image) {
    this.image = image;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }


}
