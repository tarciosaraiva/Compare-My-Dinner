package org.comparemydinner.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlushedInputStream extends FilterInputStream {

  public FlushedInputStream(final InputStream in) {
    super(in);
  }

  @Override
  public long skip(final long n) throws IOException {
    long totalBytesSkipped = 0L;

    while (totalBytesSkipped < n) {
      long bytesSkipped = in.skip(n - totalBytesSkipped);
      if (bytesSkipped == 0L) {
        final int b = read();
        if (b < 0) {
          break; // we reached EOF
        } else {
          bytesSkipped = 1; // we read one byte
        }
      }

      totalBytesSkipped += bytesSkipped;

    }

    return totalBytesSkipped;
  }

}
