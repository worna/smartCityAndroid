package com.worna.sportoo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.worna.sportoo.databinding.HomeFragmentBinding;
import com.worna.sportoo.models.NetworkError;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeViewModel viewModel;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = HomeFragmentBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);


        binding.loadCourseButton.setOnClickListener(view -> this.sendRequest());

        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);

        return binding.getRoot();
    }

    private void sendRequest() {
        viewModel.getToken();
        binding.courseErrorImage.setVisibility(View.VISIBLE);
        binding.courseInfoLayout.setVisibility(View.GONE);
        binding.courseErrorLayout.setVisibility(View.GONE);
    }

    private void displayErrorScreen(NetworkError error) {
        if (error == null) {
            binding.courseInfoLayout.setVisibility(View.VISIBLE);
            binding.courseErrorLayout.setVisibility(View.GONE);
            return;
        }

        binding.courseErrorLayout.setVisibility(View.VISIBLE);
        binding.courseInfoLayout.setVisibility(View.GONE);
        binding.courseErrorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.courseErrorText.setText(error.getErrorMessage());
    }


}
