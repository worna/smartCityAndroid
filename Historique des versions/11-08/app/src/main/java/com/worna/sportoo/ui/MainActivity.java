package com.worna.sportoo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.badge.BadgeDrawable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.worna.sportoo.R;
import com.worna.sportoo.ui.courses.CoursesFragment;
import com.worna.sportoo.ui.home.HomeFragment;
import com.worna.sportoo.ui.profile.ProfileFragment;
import com.worna.sportoo.ui.search.SearchFragment;
import com.worna.sportoo.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_item_courses);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(17);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragment());
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_item_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_item_search:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_item_courses:
                    fragment = new CoursesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_item_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_item_settings:
                    fragment = new SettingsFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}