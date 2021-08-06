package com.worna.sportoo.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.worna.sportoo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.page_1);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(1117);

    }
}