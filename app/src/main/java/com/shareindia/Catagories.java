package com.shareindia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Catagories extends AppCompatActivity implements CatagoriesAdapter.ItemClickListener {
    private Toolbar  cat_toolbar;

    private RecyclerView cat_recycleViw;
    CatagoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories);


        cat_toolbar=findViewById(R.id.cat_toolbar);
        setSupportActionBar(cat_toolbar);
//        adding the title which we will get from the main activity
        String cat_name = getIntent().getStringExtra("cat_name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(cat_name);

//        display the back button on the activity to go back to home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        taking the reference of the grid view
        cat_recycleViw=findViewById(R.id.cat_recyclerView);
        int number_of_cloumn=2;


//        creating the arraylist of type categories_list_item
        final ArrayList<Catagories_items>cat_items=Data.assign_cat_item();

//
//        adapter knows how to create list items for each item in the list.
//        CatagoriesAdapter adapter = new CatagoriesAdapter(this, cat_items);


//        cat_GridVie.setAdapter(adapter);
        cat_recycleViw.setLayoutManager(new GridLayoutManager(this,number_of_cloumn));
        adapter=new CatagoriesAdapter(this,cat_items);
        adapter.setmClickListener(this);
        cat_recycleViw.setAdapter(adapter);

    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You added" +
            adapter.getItem(position).getCat_item_name() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_categaries, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "cart" menu option

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}