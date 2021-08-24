package com.worna.sportoo.ui.courses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.worna.sportoo.R;
import com.worna.sportoo.databinding.CoursesFragmentBinding;
import com.worna.sportoo.models.Course;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.ui.MainActivity;
import com.worna.sportoo.ui.search.SearchCourseFragment;

import java.util.List;

public class CoursesFragment extends Fragment {

    private CoursesFragmentBinding binding;
    private Handler mHandler;
    CoursesViewModel viewModel;
    CourseAdapter mAdapter;

    public CoursesFragment() {
    }

    private Runnable updateAdapterRunnable = new Runnable() {
        @Override
        public void run() {
            sendRequestGetCourses();
            viewModel.getCourses().observe(getViewLifecycleOwner(), courseList -> {
                mAdapter.setCourses(courseList);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
            });
            viewModel.getError().observe(getViewLifecycleOwner(), CoursesFragment.this::displayErrorScreen);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = CoursesFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        mAdapter = new CourseAdapter();

        mAdapter.setOnItemClickListener((position, v) -> {
            Bundle searchSportHallArgs = SearchCourseFragment.newArguments(mAdapter.getItem(position));
            Navigation.findNavController(v)
                    .navigate(R.id.action_coursesFragment_to_searchCourseFragment, searchSportHallArgs);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setEmptyView(binding.emptyView);
        binding.recyclerView.setAdapter(mAdapter);
        mHandler = new Handler();

        return binding.getRoot();
    }
    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setToolBarTitle(getString(R.string.courses));
        super.onResume();
        updateAdapterRunnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(updateAdapterRunnable);
    }

    private void sendRequestGetCourses(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "vide");
        viewModel.getNextCoursesFromWeb(email);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);

    }

    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> this.sendRequestGetCourses());
    }

    private static class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder>{
        private List<Course> mCourses;
        private static ClickListener clickListener;

        public void setCourses(List<Course> notes) {
            this.mCourses = notes;
            notifyDataSetChanged();
        }

        @Override
        public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_clear_layout, parent, false);

            return new CourseViewHolder(itemView, clickListener);
        }

        @Override
        public void onBindViewHolder(CourseViewHolder holder, int position) {
            holder.mCourseClearName.setText(mCourses.get(position).activity);
            holder.mCourseClearDate.setText(mCourses.get(position).startingDateTime.toString());
            holder.mCourseClearAddress.setText(mCourses.get(position).sportHall.getCompletAddress());
        }

        @Override
        public int getItemCount() {
            return mCourses == null ? 0 : mCourses.size();
        }

        public Course getItem(int position) {
            return mCourses != null ? mCourses.get(position) : null;
        }

        public void setOnItemClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(int position, View v);
        }
    }

    private static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CourseAdapter.ClickListener clickListener;
        protected TextView mCourseClearName;
        protected TextView mCourseClearDate;
        protected TextView mCourseClearAddress;

        CourseViewHolder(View itemView, CourseAdapter.ClickListener clickListener) {
            super(itemView);
            mCourseClearName = (TextView) itemView.findViewById(R.id.course_clear_name);
            mCourseClearDate = (TextView) itemView.findViewById(R.id.course_clear_date);
            mCourseClearAddress = (TextView) itemView.findViewById(R.id.course_clear_address);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), itemView);
        }
    }



}
