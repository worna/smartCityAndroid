package com.worna.sportoo.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.worna.sportoo.R;
import com.worna.sportoo.databinding.HomeFragmentBinding;
import com.worna.sportoo.models.Course;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.ui.MainActivity;

import java.util.List;

public class HomeFragment extends Fragment{

    private HomeFragmentBinding binding;
    private RecyclerView homeRecyclerView;
    private HomeViewModel viewModel;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = HomeFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        UserAdapter adapter = new UserAdapter();

        sendRequestGetCourses();
        viewModel.getCourses().observe(getViewLifecycleOwner(), courseList -> {
                adapter.setCourses(courseList);
                binding.progressBar.setVisibility(View.GONE);
                binding.listOfNextCoursesRecyclerView.setVisibility(View.VISIBLE);
        });
        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);

        binding.listOfNextCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.listOfNextCoursesRecyclerView.setAdapter(adapter);

        binding.imageButton.setOnClickListener(view ->  Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_mapFragment));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setToolBarTitle(getString(R.string.menu_home));
        super.onResume();
    }

    private void sendRequestGetCourses(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        viewModel.getNextCoursesFromWeb(email);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.listOfNextCoursesRecyclerView.setVisibility(View.GONE);
    }


    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            binding.listOfNextCoursesRecyclerView.setVisibility(View.VISIBLE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.listOfNextCoursesRecyclerView.setVisibility(View.GONE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> this.sendRequestGetCourses());
    }

    private static class HomeViewHolder extends RecyclerView.ViewHolder {

        // Declare all the fields of a single item here.
        public TextView name;
        public TextView time;
        public TextView address;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.course_clear_name);
            time = itemView.findViewById(R.id.course_clear_date);
            address = itemView.findViewById(R.id.course_clear_address);
        }
    }

    /**
     * Create an Adapter in the Activity, that will handle changes and layouts for each item
     */
    private static class UserAdapter extends RecyclerView.Adapter<HomeViewHolder> {

        // Add the Java list you want to show, as a private variable
        private List<Course> myCourses;

        //Implement the method OnCreateViewHolder, in the Adapter
        @NonNull
        @Override
        public HomeFragment.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_clear_layout, parent, false);
            HomeViewHolder vh = new HomeViewHolder(v);
            return vh;
        }

        //Implement the method OnBindViewHolder, in the Adapter.
        //Bind all the fields of the ViewHolder in it
        @Override
        public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
            Course c = myCourses.get(position);
            holder.name.setText(c.activity);
            holder.time.setText(c.startingDateTime.toString() + " - " + c.endingDateTime.toString());
            holder.address.setText(c.sportHall.getCompletAddress());
        }

        //Implement the method GetItemCount, in the Adapter
        @Override
        public int getItemCount() {
            return myCourses == null ? 0 : myCourses.size();
        }


        //Add a setter for the private list in the Adapter. Call notifyDataSetChanged at the end of the setter
        public void setCourses(List<Course> myCourses) {
            this.myCourses = myCourses;
            notifyDataSetChanged();
        }
    }


}
