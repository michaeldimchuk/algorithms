package org.michaeldimchuk.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

  private static final Gson gson = new GsonBuilder()
      .disableHtmlEscaping()
      .serializeNulls()
      .setLenient()
      .create();

  public static Gson gson() {
    return gson;
  }
}
