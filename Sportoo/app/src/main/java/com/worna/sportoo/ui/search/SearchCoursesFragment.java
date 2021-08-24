package com.worna.sportoo.ui.search;

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
import com.worna.sportoo.databinding.SearchCoursesFragmentBinding;
import com.worna.sportoo.models.Course;
import com.worna.sportoo.ui.MainActivity;
import com.worna.sportoo.ui.courses.EmptyRecyclerView;

import java.util.List;

public class SearchCoursesFragment extends Fragment {

    private static final String ARG_SPORT_HALL_ID = "sport_hall_id";
    SearchCoursesFragmentBinding binding;
    SearchCoursesViewModel viewModel;
    SearchCourseAdapter mAdapter;
    private int id;
    private Handler mHandler;
    private Runnable updateAdapterRunnable = new Runnable() {
        @Override
        public void run() {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("email", "vide");
            viewModel.getSportHallCourseFromWeb(id);
            viewModel.getCourses().observe(getViewLifecycleOwner(), mAdapter::setCourse);
        }
    };


    public SearchCoursesFragment() {
    }

    static Bundle newArguments(Integer sport_hall_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_SPORT_HALL_ID, sport_hall_id);

        return args;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = SearchCoursesFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SearchCoursesViewModel.class);
        mAdapter = new SearchCourseAdapter();

        mAdapter.setOnItemClickListener((position, v) -> {
            Bundle searchSportHallArgs = SearchCourseFragment.newArguments(mAdapter.getItem(position));
            Navigation.findNavController(v)
                    .navigate(R.id.action_searchCoursesFragment_to_searchCourseFragment, searchSportHallArgs);
        });

        id = getArguments().getInt(ARG_SPORT_HALL_ID);

        EmptyRecyclerView recyclerView = binding.courseRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setEmptyView(binding.emptyView);
        recyclerView.setAdapter(mAdapter);
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

    private static class SearchCourseAdapter extends RecyclerView.Adapter<SearchCoursesFragment.SearchCourseViewHolder> {
        private static ClickListener clickListener;
        private List<Course> mCourses;


        public void setCourse(List<Course> notes) {
            this.mCourses = notes;
            notifyDataSetChanged();
        }

        @Override
        public SearchCoursesFragment.SearchCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_clear_layout, parent, false);

            return new SearchCoursesFragment.SearchCourseViewHolder(itemView, clickListener);
        }

        @Override
        public void onBindViewHolder(SearchCoursesFragment.SearchCourseViewHolder holder, int position) {
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

    private static class SearchCourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SearchCourseAdapter.ClickListener clickListener;
        protected TextView mCourseClearName;
        protected TextView mCourseClearDate;
        protected TextView mCourseClearAddress;

        SearchCourseViewHolder(View itemView, SearchCourseAdapter.ClickListener clickListener) {
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
