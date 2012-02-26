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

public class SearchFoodService extends BaseFatSecretService {

  private static final String TAG = "SearchFoodService";

  private static final String SEARCH_EXPRESSION = "search_expression=";

  private static final String SEARCH_RECIPE = "foods.search";

  @Override
  public String buildQueryForMethod(final String criteria, final OAuthConsumer consumer) {

    try {
      final String url = constructSearchUrl(criteria);
      final String signedUrl = consumer.sign(url);

      final HttpResponse response = client.execute(new HttpGet(signedUrl));

      final HttpEntity getResponseEntity = response.getEntity();

      if (getResponseEntity != null) {
        return EntityUtils.toString(getResponseEntity);
      }

    } catch (final OAuthMessageSignerException e) {
      Log.e(TAG, e.getMessage());
    } catch (final OAuthExpectationFailedException e) {
      Log.e(TAG, e.getMessage());
    } catch (final OAuthCommunicationException e) {
      Log.e(TAG, e.getMessage());
    } catch (final IOException e) {
      Log.e(TAG, e.getMessage());
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
