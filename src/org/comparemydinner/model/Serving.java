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

public class Serving {

  private long serving_id;

  private String serving_description;

  private float metric_serving_amount;

  private String metric_serving_unit;

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

  public long getServing_id() {
    return serving_id;
  }

  public void setServing_id(final long serving_id) {
    this.serving_id = serving_id;
  }

  public String getServing_description() {
    return serving_description;
  }

  public void setServing_description(final String serving_description) {
    this.serving_description = serving_description;
  }

  public float getMetric_serving_amount() {
    return metric_serving_amount;
  }

  public void setMetric_serving_amount(final float metric_serving_amount) {
    this.metric_serving_amount = metric_serving_amount;
  }

  public String getMetric_serving_unit() {
    return metric_serving_unit;
  }

  public void setMetric_serving_unit(final String metric_serving_unit) {
    this.metric_serving_unit = metric_serving_unit;
  }

  public float getCalories() {
    return calories;
  }

  public void setCalories(final float calories) {
    this.calories = calories;
  }

  public float getCarbohydrate() {
    return carbohydrate;
  }

  public void setCarbohydrate(final float carbohydrate) {
    this.carbohydrate = carbohydrate;
  }

  public float getProtein() {
    return protein;
  }

  public void setProtein(final float protein) {
    this.protein = protein;
  }

  public float getFat() {
    return fat;
  }

  public void setFat(final float fat) {
    this.fat = fat;
  }

  public float getSaturated_fat() {
    return saturated_fat;
  }

  public void setSaturated_fat(final float saturated_fat) {
    this.saturated_fat = saturated_fat;
  }

  public float getPolyunsaturated_fat() {
    return polyunsaturated_fat;
  }

  public void setPolyunsaturated_fat(final float polyunsaturated_fat) {
    this.polyunsaturated_fat = polyunsaturated_fat;
  }

  public float getMonounsaturated_fat() {
    return monounsaturated_fat;
  }

  public void setMonounsaturated_fat(final float monounsaturated_fat) {
    this.monounsaturated_fat = monounsaturated_fat;
  }

  public float getTrans_fat() {
    return trans_fat;
  }

  public void setTrans_fat(final float trans_fat) {
    this.trans_fat = trans_fat;
  }

  public float getCholesterol() {
    return cholesterol;
  }

  public void setCholesterol(final float cholesterol) {
    this.cholesterol = cholesterol;
  }

  public float getSodium() {
    return sodium;
  }

  public void setSodium(final float sodium) {
    this.sodium = sodium;
  }

  public float getPotassium() {
    return potassium;
  }

  public void setPotassium(final float potassium) {
    this.potassium = potassium;
  }

  public float getFiber() {
    return fiber;
  }

  public void setFiber(final float fiber) {
    this.fiber = fiber;
  }

  public float getSugar() {
    return sugar;
  }

  public void setSugar(final float sugar) {
    this.sugar = sugar;
  }

  public int getVitamin_a() {
    return vitamin_a;
  }

  public void setVitamin_a(final int vitamin_a) {
    this.vitamin_a = vitamin_a;
  }

  public int getVitamin_c() {
    return vitamin_c;
  }

  public void setVitamin_c(final int vitamin_c) {
    this.vitamin_c = vitamin_c;
  }

  public int getCalcium() {
    return calcium;
  }

  public void setCalcium(final int calcium) {
    this.calcium = calcium;
  }

  public int getIron() {
    return iron;
  }

  public void setIron(final int iron) {
    this.iron = iron;
  }

}
