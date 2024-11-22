package com.example.zolaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zolaapp.R;
import com.example.zolaapp.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ZolaActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView navigationView;
    private String email="";
    private EditText search_bar;

    public String getEmail() {
        return email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zola);
        viewPager=findViewById(R.id.view_pager);
        navigationView=findViewById(R.id.bottom_navigation);
        search_bar=findViewById(R.id.search_bar);
        ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        initControl();
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ZolaActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initControl() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position==0){
                    navigationView.getMenu().findItem(R.id.nav_messages).setChecked(true);
                } else if (position==1) {
                    navigationView.getMenu().findItem(R.id.nav_contacts).setChecked(true);
                } else if (position==2) {
                    navigationView.getMenu().findItem(R.id.nav_explore).setChecked(true);
                } else if (position==3) {
                    navigationView.getMenu().findItem(R.id.nav_diary).setChecked(true);
                } else if (position==4) {
                    navigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
                }
            }
        });
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_messages) {
                    viewPager.setCurrentItem(0, false); // Smooth transition with animation
                } else if (itemId == R.id.nav_contacts) {
                    viewPager.setCurrentItem(1, false); // Smooth transition with animation
                } else if (itemId == R.id.nav_explore) {
                    viewPager.setCurrentItem(2, false); // Smooth transition with animation
                } else if (itemId == R.id.nav_diary) {
                    viewPager.setCurrentItem(3, false); // Smooth transition with animation
                } else if (itemId == R.id.nav_profile) {
                    viewPager.setCurrentItem(4, false); // Smooth transition with animation
                }
                return true;
            }
        });
    }
}