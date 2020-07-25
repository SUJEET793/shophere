package com.shophere;
// this is the class which refence of the xml file .eg categaries_item.xml
public class Catagories_items {

    private int cat_item_image;

    private String cat_item_name;

//    constructor for that


    public Catagories_items(int cat_item_image, String cat_item_name) {
        this.cat_item_image = cat_item_image;
        this.cat_item_name = cat_item_name;
    }

//    getter function for the elemnet


    public int getCat_item_image() {
        return cat_item_image;
    }

    public String getCat_item_name() {
        return cat_item_name;
    }
}
