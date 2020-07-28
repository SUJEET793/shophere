package com.shophere;

import android.content.Context;

public class Main_list_item {
  //  image that to display
  private String main_item_image;

  private String main_item_name;
  public  Main_list_item(){}

  public Main_list_item(String main_item_image, String main_item_name) {
    this.main_item_image = main_item_image;
    this.main_item_name = main_item_name;
  }

  public void setMain_item_image(String main_item_image) {
    this.main_item_image = main_item_image;
  }

  public void setMain_item_name(String main_item_name) {
    this.main_item_name = main_item_name;
  }

  public String getMain_item_image() {
    return main_item_image;
  }

  public String getMain_item_name() {
    return main_item_name;
  }
}