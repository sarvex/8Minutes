package com.eightmins.eightminutes.advocate.member;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nabhilax on 14/01/16.
 */
public class Team implements Parcelable {
  public static final Creator<Team> CREATOR = new Creator<Team>() {
    @Override
    public Team createFromParcel(final Parcel parcel) {
      return new Team(parcel);
    }

    @Override
    public Team[] newArray(final int size) {
      return new Team[size];
    }
  };

  private int image;
  private String name;
  private int installed;
  private int progress;
  private int pending;
  private long earnings;

  public Team() {

  }

  public Team(Parcel parcel) {

  }

  public Team (int image, String name, int installed, int progress, int pending, long earnings) {
    this.image = image;
    this.name = name;
    this.installed = installed;
    this.progress = progress;
    this.pending = pending;
    this.earnings = earnings;
  }

  public static Creator<Team> getCREATOR() {
    return CREATOR;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

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

  public int getInstalled() {
    return installed;
  }

  public void setInstalled(int installed) {
    this.installed = installed;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public int getPending() {
    return pending;
  }

  public void setPending(int pending) {
    this.pending = pending;
  }

  public long getEarnings() {
    return earnings;
  }

  public void setEarnings(long earnings) {
    this.earnings = earnings;
  }
}
