package com.worna.sportoo.ui.profile;

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

import com.worna.sportoo.R;
import com.worna.sportoo.databinding.ProfileFragmentBinding;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.ui.MainActivity;
import com.worna.sportoo.ui.login.LoginActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding binding;
    private ProfileViewModel viewModel;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.logOut.setOnClickListener(this::navigateToLogin);
        this.sendRequestGetCustomer();
        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);



        return binding.getRoot();
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setToolBarTitle(getString(R.string.menu_profile));
        super.onResume();
    }

    private void sendRequestGetCustomer() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        viewModel.getCustomerFromWeb(email);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.profileInfoLayout.setVisibility(View.GONE);
    }

    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            binding.profileInfoLayout.setVisibility(View.VISIBLE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            setValues();
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.profileInfoLayout.setVisibility(View.GONE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> this.sendRequestGetCustomer());
    }

    private void setValues(){
        Integer gender = viewModel.getAccount().getValue().gender;
        String[] mGenderArray = getResources().getStringArray(R.array.gender_array);
        binding.gender.setText(mGenderArray[gender]);

        Date birthdate = viewModel.getAccount().getValue().birthDate;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(birthdate);
        binding.birthdate.setText(date);
    }

    private void navigateToLogin(View view){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
