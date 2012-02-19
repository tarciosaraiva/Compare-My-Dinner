package org.comparemydinner.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MultipleServings {

  @SerializedName("serving")
  private List<Serving> servingList;

  public MultipleServings(List<Serving> servingList) {
    this.servingList = servingList;
  }

  public List<Serving> getServingList() {
    return servingList;
  }

  public void setServingList(List<Serving> servingList) {
    this.servingList = servingList;
  }

}
