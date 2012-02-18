package org.comparemydinner.model;

public class Recipe {

  private long recipe_id;

  private String recipe_name;

  private String recipe_description;

  private String recipe_url;

  public long getRecipe_id() {
    return recipe_id;
  }

  public void setRecipe_id(long recipe_id) {
    this.recipe_id = recipe_id;
  }

  public String getRecipe_name() {
    return recipe_name;
  }

  public void setRecipe_name(String recipe_name) {
    this.recipe_name = recipe_name;
  }

  public String getRecipe_description() {
    return recipe_description;
  }

  public void setRecipe_description(String recipe_description) {
    this.recipe_description = recipe_description;
  }

  public String getRecipe_url() {
    return recipe_url;
  }

  public void setRecipe_url(String recipe_url) {
    this.recipe_url = recipe_url;
  }

  @Override
  public String toString() {
    return recipe_name;
  }

}
