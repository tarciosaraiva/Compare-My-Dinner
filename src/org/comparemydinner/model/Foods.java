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

public class Foods {

  private int total_results;

  private Food[] food;

  public Food[] getFood() {
    return food;
  }

  public void setFood(final Food[] food) {
    this.food = food;
  }

  public int getTotal_results() {
    return total_results;
  }

  public void setTotal_results(final int total_results) {
    this.total_results = total_results;
  }

}
