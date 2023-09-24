package com.example.noteclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.noteclock.Adapter.ViewpageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewpager2;
    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mViewpager2.setAdapter(new ViewpageAdapter(this));

        mViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.mToday).setChecked(true);
                        break;
                }
            }
        });

        mBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.mHome) {
                mViewpager2.setCurrentItem(0);
            } else if (item.getItemId() == R.id.mToday) {
                mViewpager2.setCurrentItem(1);
            } else {
                mViewpager2.setCurrentItem(2);
            }
            return true;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Ôk",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews() {
        mBottomNavigationView = findViewById(R.id.bottom_nav);
        mViewpager2 = findViewById(R.id.view_page_2);
        fab = findViewById(R.id.fab_add);
    }
}