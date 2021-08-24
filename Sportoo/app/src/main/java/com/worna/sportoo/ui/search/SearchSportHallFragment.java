package com.worna.sportoo.ui.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.worna.sportoo.R;
import com.worna.sportoo.databinding.SearchSportHallFragmentBinding;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.models.SportHall;
import com.worna.sportoo.ui.MainActivity;

public class SearchSportHallFragment extends Fragment {

    private static final String ARG_SPORT_HALL_ID = "sport_hall_id";
    private static final String ARG_SPORT_HALL_NAME = "sport_hall_name";
    private static final String ARG_SPORT_HALL_ADDRESS = "sport_hall_address";
    private static final String ARG_SPORT_HALL_EMAIL = "sport_hall_email";
    private static final String ARG_SPORT_HALL_PHONE_NUMBER = "sport_hall_phone_number";

    private int id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isReg;

    SearchSportHallFragmentBinding binding;
    SearchSportHallViewModel viewModel;

    static Bundle newArguments(SportHall sportHall) {
        Bundle args = new Bundle();
        args.putInt(ARG_SPORT_HALL_ID, sportHall.id);
        args.putString(ARG_SPORT_HALL_NAME, sportHall.name);
        args.putString(ARG_SPORT_HALL_ADDRESS, sportHall.getCompletAddress());
        args.putString(ARG_SPORT_HALL_EMAIL, sportHall.email);
        args.putString(ARG_SPORT_HALL_PHONE_NUMBER, sportHall.phoneNumber);
        return args;
    }


    public SearchSportHallFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SearchSportHallFragmentBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(SearchSportHallViewModel.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String customerEmail = sharedPreferences.getString("email", "vide");

        id = getArguments().getInt(ARG_SPORT_HALL_ID);
        name = getArguments().getString(ARG_SPORT_HALL_NAME);
        address = getArguments().getString(ARG_SPORT_HALL_ADDRESS);
        email = getArguments().getString(ARG_SPORT_HALL_EMAIL);
        phoneNumber = getArguments().getString(ARG_SPORT_HALL_PHONE_NUMBER);

        sendRequestIsSportHallCustomerExist();
        viewModel.getIsRegistred().observe(getViewLifecycleOwner(), isRegistered -> {
            if(isRegistered){
                binding.sportHallSubscribeButton.setText(R.string.unsubscribe);
                binding.sportHallSubscribeButton.setBackgroundColor(getResources().getColor(R.color.red));
                binding.sportHallCoursesButton.setVisibility(View.VISIBLE);
            } else {
                binding.sportHallSubscribeButton.setText(R.string.subscribe);
                binding.sportHallSubscribeButton.setBackgroundColor(getResources().getColor(R.color.blue));
                binding.sportHallCoursesButton.setVisibility(View.GONE);
            }
        });
        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);

        Bundle searchCourseArgs = SearchCoursesFragment.newArguments(id);
        binding.sportHallCoursesButton.setOnClickListener(view ->  Navigation.findNavController(view)
                .navigate(R.id.action_searchFragmentSportHall_to_searchCoursesFragment, searchCourseArgs));



        binding.sportHallSubscribeButton.setOnClickListener(view -> sendRequestSubscription());

        binding.sportHallTag.setText(name);
        binding.searchFragmentSportHallEmail.setText(email);
        binding.searchFragmentSportHallPhoneNumber.setText(phoneNumber);
        binding.searchFragmentSportHallAddress.setText(address);



        return binding.getRoot();
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setToolBarTitle(name);
        super.onResume();
    }

    private void sendRequestIsSportHallCustomerExist(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        viewModel.isSportHallCustomerExistFromWeb(email, id);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.sportHallScrollView.setVisibility(View.GONE);
    }

    private void sendRequestSubscription(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        if (viewModel.getIsRegistred().getValue()){
            viewModel.deleteSportHallCustomerToWeb(email, id);
        } else {
            viewModel.addSportHallCustomerToWeb(email, id);
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.sportHallScrollView.setVisibility(View.GONE);
    }

    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            binding.sportHallScrollView.setVisibility(View.VISIBLE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.sportHallScrollView.setVisibility(View.GONE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> {
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            binding.sportHallScrollView.setVisibility(View.VISIBLE);
            this.sendRequestIsSportHallCustomerExist();
        });
    }



}
