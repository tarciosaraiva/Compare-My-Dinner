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

  public void setFood_id(long food_id) {
    this.food_id = food_id;
  }

  public String getFood_name() {
    return food_name;
  }

  public void setFood_name(String food_name) {
    this.food_name = food_name;
  }

  public String getFood_description() {
    return food_description;
  }

  public void setFood_description(String food_description) {
    this.food_description = food_description;
  }

  public Servings getServings() {
    return servings;
  }

  public void setServings(Servings servings) {
    this.servings = servings;
  }

  public String getFood_url() {
    return food_url;
  }

  public void setFood_url(String food_url) {
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
