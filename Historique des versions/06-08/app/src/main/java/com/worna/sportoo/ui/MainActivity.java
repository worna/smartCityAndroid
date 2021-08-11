package com.worna.sportoo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.worna.sportoo.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.page_1);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(1117);

        /*
        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.page_1:
                        Log.i(TAG,"Accueil");
                        return true;
                    case R.id.page_2:
                        Log.i(TAG,"Accueil");
                        return true;
                    case R.id.page_3:
                        Log.i(TAG,"Accueil");
                        return true;
                    case R.id.page_4:
                        Log.i(TAG,"Accueil");
                        return true;
                    case R.id.page_5:
                        Log.i(TAG,"Settings");
                        view
                        goToNext();
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        */

        View menuItem1 = findViewById(R.id.page_1);
        menuItem1.setOnClickListener(this::goToNext);
        View menuItem5 = findViewById(R.id.page_5);
        menuItem5.setOnClickListener(this::goToNext);
    }

    protected void goToNext(View view) {
        Log.i(TAG, Integer.toString(view.getId()));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (view.getId()){
            case 2131231003:
                navController.navigate(R.id.action_settingsFragment_to_homeFragment);
                break;
            case 2131231007:
                navController.navigate(R.id.action_homeFragment_to_settingsFragment);
                break;
        }
    }
}