package com.worna.sportoo.ui.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.worna.sportoo.R;
import com.worna.sportoo.ui.MainActivity;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        ImageView notifications = root.findViewById(R.id.settings_notifications_arrow);
        ImageView visibility = root.findViewById(R.id.settings_visibility_arrow);
        ImageView security = root.findViewById(R.id.settings_security_arrow);
        ImageView aboutUs = root.findViewById(R.id.settings_about_us_arrow);

        notifications.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_settingsFragment_to_notImplementedFragment));
        visibility.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_settingsFragment_to_notImplementedFragment));
        security.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_settingsFragment_to_notImplementedFragment));
        aboutUs.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_settingsFragment_to_aboutUsFragment));


        Button darkModeButton = root.findViewById(R.id.settings_dark_mode_button);

        darkModeButton.setOnClickListener( view -> {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setToolBarTitle(getString(R.string.menu_settings));
        super.onResume();
    }
}
