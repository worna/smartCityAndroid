package com.worna.sportoo.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.worna.sportoo.R;
import com.worna.sportoo.databinding.SearchFragmentBinding;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.models.SportHall;
import com.worna.sportoo.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    SearchFragmentBinding binding;
    SearchViewModel viewModel;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        UserAdapter adapter = new UserAdapter();


        adapter.setOnItemClickListener((position, v) -> {
            Bundle searchSportHallArgs = SearchSportHallFragment.newArguments(adapter.getItem(position));
            Navigation.findNavController(v)
                    .navigate(R.id.action_searchFragment_to_searchFragmentSportHall, searchSportHallArgs);
        });
        sendRequestGetSportHalls();
        viewModel.getSportHall().observe(getViewLifecycleOwner(), sportHallList -> {
            adapter.setSportHall(sportHallList);
            binding.progressBar.setVisibility(View.GONE);
            binding.searchContainer.setVisibility(View.VISIBLE);
        });
        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);
        binding.listOfSportHallRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        binding.listOfSportHallRecyclerView.setAdapter(adapter);

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });


        ((MainActivity) getActivity()).setToolBarTitle(getString(R.string.menu_search));
        return binding.getRoot();
    }

    private void sendRequestGetSportHalls(){
        viewModel.getSportHallFromWeb();
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.searchContainer.setVisibility(View.GONE);
    }

    private void displayErrorScreen(NetworkError error) {
        binding.progressBar.setVisibility(View.GONE);
        if (error == null) {
            binding.searchContainer.setVisibility(View.VISIBLE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
            return;
        }
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.searchContainer.setVisibility(View.GONE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> this.sendRequestGetSportHalls());
    }


    // ----------------- ADAPTER FOR RECYCLERVIEW -------------------------
    /**
     * Create a private ViewHolder in the Activity, that describes a single element
     */
    private static class SportHallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Declare all the fields of a single item here.
        public TextView name;
        public ImageView image;
        public TextView address;
        public UserAdapter.ClickListener clickListener;



        public SportHallViewHolder(@NonNull View itemView, UserAdapter.ClickListener clickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.sport_hall_name);
            image = itemView.findViewById(R.id.sport_hall_image);
            address = itemView.findViewById(R.id.sport_hall_address);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), itemView);
        }
    }

    /**
     * Create an Adapter in the Activity, that will handle changes and layouts for each item
     */
    private static class UserAdapter extends RecyclerView.Adapter<SportHallViewHolder> implements Filterable {

        // Add the Java list you want to show, as a private variable
        private List<SportHall> mySportHall;
        private List<SportHall> filterList;
        private CustomFilter filter;
        private static ClickListener clickListener;


        //Implement the method OnCreateViewHolder, in the Adapter
        @NonNull
        @Override
        public SportHallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sport_hall_layout, parent, false);

            return  new SportHallViewHolder(v, clickListener);
        }

        //Implement the method OnBindViewHolder, in the Adapter.
        //Bind all the fields of the ViewHolder in it
        @Override
        public void onBindViewHolder(@NonNull SportHallViewHolder holder, int position) {
            SportHall s = mySportHall.get(position);
            holder.name.setText(s.name);
            holder.image.setImageResource(R.drawable.jims);
            holder.address.setText(s.getCompletAddress());
        }

        //Implement the method GetItemCount, in the Adapter
        @Override
        public int getItemCount() {
            return mySportHall == null ? 0 : mySportHall.size();
        }

        @Override
        public Filter getFilter() {
            if(filter == null)
            {
                filter = new CustomFilter();
            }
            return filter;
        }

        public SportHall getItem(int position) {
            return mySportHall != null ? mySportHall.get(position) : null;
        }

        //Add a setter for the private list in the Adapter. Call notifyDataSetChanged at the end of the setter
        public void setSportHall(List<SportHall> mySportHall) {
            this.mySportHall = mySportHall;
            this.filterList = mySportHall;
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(ClickListener clickListener) {
            UserAdapter.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(int position, View v);
        }

        class CustomFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if(constraint != null && constraint.length()>0)
                {
                    constraint = constraint.toString().toUpperCase();
                    ArrayList<SportHall> filters = new ArrayList<>();

                    for(int i=0;i<filterList.size();i++)
                    {
                        if(
                                filterList.get(i).name.toUpperCase().contains(constraint) ||
                                filterList.get(i).cityName.toUpperCase().contains(constraint) ||
                                filterList.get(i).zipCode.toString().contains(constraint)
                        )
                        {
                            filters.add(filterList.get(i));
                        }
                    }

                    results.count=filters.size();
                    results.values=filters;
                }else
                {
                    results.count=filterList.size();
                    results.values=filterList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mySportHall = (ArrayList<SportHall>) results.values;
                notifyDataSetChanged();
            }
        }


    }
}
