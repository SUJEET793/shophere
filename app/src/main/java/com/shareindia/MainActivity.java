package com.shareindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;

import android.widget.Toast;


import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {
    private MainAdapter adapter;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    //main list view
    private RecyclerView main_list_View;
     //    caeouselview variable

    private CarouselView carouselView;

    int[] sampleImages={R.drawable.carousel_image,R.drawable.carousel_image,R.drawable.vegetable};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

        // reference of the main layout ie drawer layout
        drawer =findViewById(R.id.drawer_layout);

        //   navigation bar toogling functionality
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.open_drawer,R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        creating the arraylist of type Main_list_item
        final ArrayList<Main_list_item> main_list_items=Data.assign_main_item();

//
         main_list_View=findViewById(R.id.main_recyclerView);
//      setting the column of the gridView
        int number_of_column=2;
        main_list_View.setLayoutManager(new GridLayoutManager(this,number_of_column));

        adapter = new MainAdapter(this, main_list_items);
        adapter.setmClickListener(this);
        main_list_View.setAdapter(adapter);


        //taking the reference of caouselView
        carouselView =findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(this,"You clicked number " + adapter.getItem(position) + ", which is at cell position " + position,Toast.LENGTH_SHORT).show();
          Oparetion.openCat(this,position);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
           imageView.setImageResource(sampleImages[position]);
//            Toast.makeText(MainActivity.this, "carouselView clicked"+position, Toast.LENGTH_SHORT).show();
        }
        };

// navigation return to the place when we want
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;
    }
}