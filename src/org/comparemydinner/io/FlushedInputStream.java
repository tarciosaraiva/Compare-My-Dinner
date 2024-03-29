/*
  Copyright Java Code Geeks

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
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
