package org.comparemydinner.model;

public class Foods {

  private int total_results;

  private Food[] food;

  public Food[] getFood() {
    return food;
  }

  public void setFood(Food[] food) {
    this.food = food;
  }

  public int getTotal_results() {
    return total_results;
  }

  public void setTotal_results(int total_results) {
    this.total_results = total_results;
  }

}
