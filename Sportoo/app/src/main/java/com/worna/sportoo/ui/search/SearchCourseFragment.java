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

import com.worna.sportoo.R;
import com.worna.sportoo.databinding.SearchCourseFragmentBinding;
import com.worna.sportoo.models.Course;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.ui.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SearchCourseFragment extends Fragment {

    private static final String ARG_COURSE_ID = "course_id";
    private static final String ARG_COURSE_NAME = "course_name";
    private static final String ARG_COURSE_START_DATE = "course_start_date";
    private static final String ARG_COURSE_END_DATE = "course_end_date";
    private static final String ARG_COURSE_SPORT_HALL_ADDRESS = "course_sport_hall_address";
    private static final String ARG_COURSE_ROOM_ID = "course_room_id";
    private static final String ARG_COURSE_LEVEL = "course_level";
    private static final String ARG_COURSE_CAPACITY = "course_capacity";


    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private String sportHallAddress;
    private Integer roomId;
    private String level;
    private Integer capacity;


    SearchCourseFragmentBinding binding;
    SearchCourseViewModel viewModel;

    public static Bundle newArguments(Course course) {
        Bundle args = new Bundle();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        args.putInt(ARG_COURSE_ID, course.id);
        args.putString(ARG_COURSE_NAME, course.activity);
        args.putString(ARG_COURSE_START_DATE, formatter.format(course.startingDateTime));
        args.putString(ARG_COURSE_END_DATE, formatter.format(course.endingDateTime));
        args.putString(ARG_COURSE_SPORT_HALL_ADDRESS, course.sportHall.name + "\n" + course.sportHall.getCompletAddress());
        args.putInt(ARG_COURSE_ROOM_ID, course.room.idRoom);
        args.putString(ARG_COURSE_LEVEL, course.level);
        args.putInt(ARG_COURSE_CAPACITY, course.room.maxCapacity);
        return args;
    }


    public SearchCourseFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SearchCourseFragmentBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(SearchCourseViewModel.class);

        id = getArguments().getInt(ARG_COURSE_ID);
        name = getArguments().getString(ARG_COURSE_NAME);
        startDate = getArguments().getString(ARG_COURSE_START_DATE);
        endDate = getArguments().getString(ARG_COURSE_END_DATE);
        sportHallAddress = getArguments().getString(ARG_COURSE_SPORT_HALL_ADDRESS);
        roomId = getArguments().getInt(ARG_COURSE_ROOM_ID);
        level = getArguments().getString(ARG_COURSE_LEVEL);
        capacity = getArguments().getInt(ARG_COURSE_CAPACITY);

        sendRequestIsCustomerCourseExist();
        viewModel.getIsRegistred().observe(getViewLifecycleOwner(), isRegistered -> {
            if(isRegistered){
                binding.courseSubscribeButton.setText(R.string.unsubscribe);
                binding.courseSubscribeButton.setBackgroundColor(getResources().getColor(R.color.red));
            } else {
                binding.courseSubscribeButton.setText(R.string.subscribe);
                binding.courseSubscribeButton.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });
        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);

        binding.courseSubscribeButton.setOnClickListener(view -> sendRequestSubscription());
        binding.courseTag.setText(name);
        binding.courseStartDate.setText(startDate);
        binding.courseEndDate.setText(endDate);
        binding.courseAddress.setText(sportHallAddress);
        binding.courseRoom.setText(Integer.toString(roomId));
        binding.courseLevel.setText(level);
        binding.courseCapacity.setText(Integer.toString(capacity));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setToolBarTitle(name);
        super.onResume();
    }

    private void sendRequestIsCustomerCourseExist(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        viewModel.isCourseCustomerExistFromWeb(email, id);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.courseScrollView.setVisibility(View.GONE);
    }

    private void sendRequestSubscription(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        if (viewModel.getIsRegistred().getValue()){
            viewModel.deleteCourseCustomerToWeb(email, id);
        } else {
            viewModel.addCourseCustomerToWeb(email, id);
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.courseScrollView.setVisibility(View.GONE);
    }

    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            binding.courseScrollView.setVisibility(View.VISIBLE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.courseScrollView.setVisibility(View.GONE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> {
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            binding.courseScrollView.setVisibility(View.VISIBLE);
            this.sendRequestIsCustomerCourseExist();
        });
    }



}
