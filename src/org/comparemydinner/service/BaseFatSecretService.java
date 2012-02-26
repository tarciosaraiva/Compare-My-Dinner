/*
  Copyright 2012 Tarcio Saraiva

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
package org.comparemydinner.service;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;

import org.apache.http.impl.client.DefaultHttpClient;

public abstract class BaseFatSecretService {

  protected static final String BASE_URL = "http://platform.fatsecret.com/rest/server.api";

  protected static final String AMPERSAND = "&";

  protected static final String QUERY = "?";

  protected static final String JSON_FORMAT = "format=json";

  protected static final String METHOD_PARAM = "method=";

  protected static final String CONSUMER_SECRET = "SECRET";

  protected static final String CONSUMER_KEY = "KEY";

  protected final DefaultHttpClient client = new DefaultHttpClient();

  public String execute(final String criteria) {
    // create a consumer object and configure it with the access
    // token and token secret obtained from the service provider
    final OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

    // signing strategy specified as we want to add the signature to the URL
    consumer.setSigningStrategy(new QueryStringSigningStrategy());

    return buildQueryForMethod(criteria, consumer);
  }

  protected abstract String buildQueryForMethod(final String criteria, final OAuthConsumer consumer);

  protected abstract String getMethod();

  protected abstract String getFilterParameter();

  protected String constructSearchUrl(final String query) {
    final StringBuffer sb = new StringBuffer();

    sb.append(BASE_URL);
    sb.append(QUERY);
    sb.append(JSON_FORMAT);
    sb.append(AMPERSAND);
    sb.append(METHOD_PARAM);
    sb.append(getMethod());
    sb.append(AMPERSAND);
    sb.append(getFilterParameter());
    sb.append(encode(query));

    return sb.toString();
  }

  // solution proposed by fmucar
  // http://stackoverflow.com/questions/724043/http-url-address-encoding-in-java
  protected String encode(final String input) {
    final StringBuilder resultStr = new StringBuilder();
    for (final char ch : input.toCharArray()) {
      if (isUnsafe(ch)) {
        resultStr.append('%');
        resultStr.append(toHex(ch / 16));
        resultStr.append(toHex(ch % 16));
      } else {
        resultStr.append(ch);
      }
    }
    return resultStr.toString();
  }

  private char toHex(final int ch) {
    return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
  }

  private boolean isUnsafe(final char ch) {
    if (ch > 128 || ch < 0) {
      return true;
    }
    return " %$&+,/:;=?@<>#%".indexOf(ch) >= 0;
  }

}
