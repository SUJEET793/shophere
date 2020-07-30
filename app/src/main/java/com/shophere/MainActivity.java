package com.shophere;

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

import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shophere.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    private MainAdapter mAdapter;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    //main list view
    private RecyclerView main_list_View;

//
    private CarouselView carouselView;
//    firebase reference
    private DatabaseReference mDatabaseReference;
    private List <Main_list_item> main_list_items;

    int[] sampleImages={R.drawable.carousel_image,R.drawable.carousel_image,R.drawable.vegetable};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //toolbar added
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // reference of the main layout ie drawer layout
        drawer =findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.open_drawer,R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        main_list_View=findViewById(R.id.main_recyclerView);
        main_list_View.setHasFixedSize(true);
//      setting the column of the gridView
        int number_of_column=2;
        main_list_View.setLayoutManager(new GridLayoutManager(this,number_of_column));
//        assign the list as arraylist
        main_list_items=new ArrayList<>();

//        getting the reference of the data base
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("main");
//        adding the data to the adapter
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot :snapshot.getChildren()){
                    Main_list_item main_list_item=postSnapshot.getValue(Main_list_item.class);
//                    adding the item to the list which we get from the data base
                    main_list_items.add(main_list_item);
                }
                mAdapter=new MainAdapter(MainActivity.this,main_list_items);
                main_list_View.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
//        reference of the serch item
        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin:
                Intent intent=new Intent(this,Admin.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}