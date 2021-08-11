package com.worna.sportoo.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.worna.sportoo.R;
import com.worna.sportoo.ui.login.LoginActivity;
import com.worna.sportoo.ui.register.RegisterActivity;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        Button logout = root.findViewById(R.id.log_out);
        logout.setOnClickListener(this::navigateToLogin);

        return root;
    }

    private void navigateToLogin(View view){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
