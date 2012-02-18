package org.comparemydinner.model;

public class Recipe {

  private long recipe_id;

  private String recipe_name;

  private String recipe_description;

  private String recipe_url;

  private float number_of_servings;

  private int rating;

  private RecipeType recipe_types;

  private String recipe_image;

  private float calories;

  private float carbohydrate;

  private float protein;

  private float fat;

  private float saturated_fat;

  private float polyunsaturated_fat;

  private float monounsaturated_fat;

  private float trans_fat;

  private float cholesterol;

  private float sodium;

  private float potassium;

  private float fiber;

  private float sugar;

  private int vitamin_a;

  private int vitamin_c;

  private int calcium;

  private int iron;

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

  public float getNumber_of_servings() {
    return number_of_servings;
  }

  public void setNumber_of_servings(float number_of_servings) {
    this.number_of_servings = number_of_servings;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public RecipeType getRecipe_types() {
    return recipe_types;
  }

  public void setRecipe_types(RecipeType recipe_types) {
    this.recipe_types = recipe_types;
  }

  public String getRecipe_image() {
    return recipe_image;
  }

  public void setRecipe_image(String recipe_image) {
    this.recipe_image = recipe_image;
  }

  public float getCalories() {
    return calories;
  }

  public void setCalories(float calories) {
    this.calories = calories;
  }

  public float getCarbohydrate() {
    return carbohydrate;
  }

  public void setCarbohydrate(float carbohydrate) {
    this.carbohydrate = carbohydrate;
  }

  public float getProtein() {
    return protein;
  }

  public void setProtein(float protein) {
    this.protein = protein;
  }

  public float getFat() {
    return fat;
  }

  public void setFat(float fat) {
    this.fat = fat;
  }

  public float getSaturated_fat() {
    return saturated_fat;
  }

  public void setSaturated_fat(float saturated_fat) {
    this.saturated_fat = saturated_fat;
  }

  public float getPolyunsaturated_fat() {
    return polyunsaturated_fat;
  }

  public void setPolyunsaturated_fat(float polyunsaturated_fat) {
    this.polyunsaturated_fat = polyunsaturated_fat;
  }

  public float getMonounsaturated_fat() {
    return monounsaturated_fat;
  }

  public void setMonounsaturated_fat(float monounsaturated_fat) {
    this.monounsaturated_fat = monounsaturated_fat;
  }

  public float getTrans_fat() {
    return trans_fat;
  }

  public void setTrans_fat(float trans_fat) {
    this.trans_fat = trans_fat;
  }

  public float getCholesterol() {
    return cholesterol;
  }

  public void setCholesterol(float cholesterol) {
    this.cholesterol = cholesterol;
  }

  public float getSodium() {
    return sodium;
  }

  public void setSodium(float sodium) {
    this.sodium = sodium;
  }

  public float getPotassium() {
    return potassium;
  }

  public void setPotassium(float potassium) {
    this.potassium = potassium;
  }

  public float getFiber() {
    return fiber;
  }

  public void setFiber(float fiber) {
    this.fiber = fiber;
  }

  public float getSugar() {
    return sugar;
  }

  public void setSugar(float sugar) {
    this.sugar = sugar;
  }

  public int getVitamin_a() {
    return vitamin_a;
  }

  public void setVitamin_a(int vitamin_a) {
    this.vitamin_a = vitamin_a;
  }

  public int getVitamin_c() {
    return vitamin_c;
  }

  public void setVitamin_c(int vitamin_c) {
    this.vitamin_c = vitamin_c;
  }

  public int getCalcium() {
    return calcium;
  }

  public void setCalcium(int calcium) {
    this.calcium = calcium;
  }

  public int getIron() {
    return iron;
  }

  public void setIron(int iron) {
    this.iron = iron;
  }

  public String[] getColumnValuesForCursor() {
    return new String[] { String.valueOf(recipe_id), recipe_name, recipe_description };
  }

  @Override
  public String toString() {
    return recipe_name;
  }

}
