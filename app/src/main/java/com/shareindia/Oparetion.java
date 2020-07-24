package com.shareindia;

import android.content.Context;
import android.content.Intent;

public class Oparetion {
    public static void openCat(Context context, int position){
        final Intent intent;
        switch (position){
            case 0:
                intent=new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Vegetable");
                break;

            case 1:
                intent =  new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Fruits ");
                break;

            case 2:
                intent =  new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Groceries");
                break;
            case 3:
                intent =  new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Water Bottles");
                break;
            case 4:
                intent =  new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Milk & Products");
                break;
            case 5:
                intent =  new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Masala & Oil");
                break;
            default:
                intent =  new Intent(context,Catagories.class);
                intent.putExtra("cat_name","Rest");
                break;
        }
        context.startActivity(intent);
    }
}
