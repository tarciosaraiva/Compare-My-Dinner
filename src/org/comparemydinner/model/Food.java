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
package org.comparemydinner.model;

public class Food {

  private long food_id;

  private String food_name;

  private String food_description;

  private String food_url;

  private Servings servings;

  public long getFood_id() {
    return food_id;
  }

  public void setFood_id(final long food_id) {
    this.food_id = food_id;
  }

  public String getFood_name() {
    return food_name;
  }

  public void setFood_name(final String food_name) {
    this.food_name = food_name;
  }

  public String getFood_description() {
    return food_description;
  }

  public void setFood_description(final String food_description) {
    this.food_description = food_description;
  }

  public Servings getServings() {
    return servings;
  }

  public void setServings(final Servings servings) {
    this.servings = servings;
  }

  public String getFood_url() {
    return food_url;
  }

  public void setFood_url(final String food_url) {
    this.food_url = food_url;
  }

  public String[] getColumnValuesForCursor() {
    return new String[] { String.valueOf(food_id), food_name, food_description };
  }

  @Override
  public String toString() {
    return food_name;
  }

}
