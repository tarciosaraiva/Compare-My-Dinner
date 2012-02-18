package org.comparemydinner.util;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

  public static void closeStreamQuietly(final InputStream inputStream) {
    try {
      if (inputStream != null) {
        inputStream.close();
      }
    } catch (final IOException e) {
      // ignore exception
    }
  }

}
