package org.comparemydinner.service;

import java.io.IOException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class SearchRecipeService extends BaseFatSecretService {

  private static final String SEARCH_EXPRESSION = "search_expression=";

  private static final String SEARCH_RECIPE = "recipes.search";

  @Override
  public String buildQueryForMethod(final String criteria, final OAuthConsumer consumer) {

    try {
      String url = constructSearchUrl(criteria);
      String signedUrl = consumer.sign(url);

      final HttpResponse response = client.execute(new HttpGet(signedUrl));

      final HttpEntity getResponseEntity = response.getEntity();

      if (getResponseEntity != null) {
        return EntityUtils.toString(getResponseEntity);
      }

    } catch (final OAuthMessageSignerException e) {
      Log.e("FSS", e.getMessage());
    } catch (final OAuthExpectationFailedException e) {
      Log.e("FSS", e.getMessage());
    } catch (final OAuthCommunicationException e) {
      Log.e("FSS", e.getMessage());
    } catch (final IOException e) {
      Log.e("FSS", e.getMessage());
    }

    return null;
  }

  @Override
  protected String getMethod() {
    return SEARCH_RECIPE;
  }

  @Override
  protected String getFilterParameter() {
    return SEARCH_EXPRESSION;
  }

}
