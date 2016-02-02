package com.eightmins.eightminutes.utility;

import java.util.regex.Pattern;

/**
 * Created by Sarvex on 2/1/2016.
 */
public class Validate {

  private static final Pattern NAME_PATTERN
      = Pattern.compile("^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$");
  private static final Pattern USERNAME_PATTERN = Pattern.compile("");
  private static final Pattern PASSWORD_PATTERN = Pattern.compile("");
  private static final Pattern PHONE_PATTERN = Pattern.compile("");
  private static final Pattern EMAIL_PATTERN = Pattern.compile("");
  private static final Pattern ADDRESS_PATTERN = Pattern.compile("");
  private static final Pattern LOCALITY_PATTERN = Pattern.compile("");
  private static final Pattern PINCODE_PATTERN = Pattern.compile("");
  private static final Pattern CITY_PATTERN = Pattern.compile("");
  private static final Pattern BILL_PATTERN = Pattern.compile("");
  private static final Pattern NOTES_PATTERN = Pattern.compile("");

  public static boolean name (String name) {

    return NAME_PATTERN.matcher(name).matches();
  }

  public static boolean username (String username) {

    return USERNAME_PATTERN.matcher(username).matches();
  }

  public static boolean password (String password) {

    return PASSWORD_PATTERN.matcher(password).matches();
  }

  public static boolean confirm (String confirm, String password) {

    return confirm != null && confirm.equals(password);
  }

  public static boolean phone (String phone) {

    return PHONE_PATTERN.matcher(phone).matches();
  }

  public static boolean email (String email) {

    return EMAIL_PATTERN.matcher(email).matches();
  }

  public static boolean address (String address) {

    return ADDRESS_PATTERN.matcher(address).matches();
  }

  public static boolean locality (String locality) {

    return LOCALITY_PATTERN.matcher(locality).matches();
  }

  public static boolean pincode (String pincode) {

    return PINCODE_PATTERN.matcher(pincode).matches();
  }

  public static boolean city (String city) {

    return CITY_PATTERN.matcher(city).matches();
  }

  public static boolean bill (String bill) {

    return BILL_PATTERN.matcher(bill).matches();
  }

  public static boolean notes (String notes) {

    return NOTES_PATTERN.matcher(notes).matches();
  }
}
