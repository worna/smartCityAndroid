package com.worna.sportoo.ui.registered;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.worna.sportoo.databinding.RegisteredFragmentBinding;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.ui.MainActivity;
import com.worna.sportoo.ui.login.RegisteredViewModel;

public class RegisteredFragment extends Fragment {

    private static final String ARG_CUSTOMER_EMAIL = "customer_email";
    private static final String ARG_CUSTOMER_PASSWORD = "customer_password";

    private RegisteredFragmentBinding binding;
    private RegisteredViewModel viewModel;


    private String email;
    private String password;


    public static Bundle newArguments(String email, String password) {
        Bundle args = new Bundle();
        args.putString(ARG_CUSTOMER_EMAIL, email);
        args.putString(ARG_CUSTOMER_PASSWORD, password);
        return args;
    }

    public RegisteredFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RegisteredViewModel.class);
        binding = RegisteredFragmentBinding.inflate(inflater, container, false);
        email = getArguments().getString(ARG_CUSTOMER_EMAIL);
        password = getArguments().getString(ARG_CUSTOMER_PASSWORD);

        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);

        binding.logIn.setOnClickListener(view -> {
            sendRequestGetToken();
        });
        return binding.getRoot();
    }

    private void sendRequestGetToken(){

        viewModel.getTokenFromWeb(email, password);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.infos.setVisibility(View.GONE);
        binding.logIn.setVisibility(View.GONE);
    }


    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            SharedPreferences sharedPref = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("token", viewModel.getToken().getValue());
            editor.putString("email", email);
            editor.commit();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> {
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            binding.infos.setVisibility(View.VISIBLE);
            binding.logIn.setVisibility(View.VISIBLE);
        });

    }

}
