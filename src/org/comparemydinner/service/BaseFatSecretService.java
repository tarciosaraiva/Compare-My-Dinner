package org.comparemydinner.service;

import java.net.URLEncoder;

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

  protected static final String CONSUMER_SECRET = "5a67b9df49a94ed5a239a99a1b63f2c0";

  protected static final String CONSUMER_KEY = "774802266b47489ca10e114fc8da6f36";

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
    sb.append(URLEncoder.encode(query));

    return sb.toString();
  }

}
