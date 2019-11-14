package com.example.ecomerce_diplomado.ui.category;


import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryFragment extends Fragment {
    private int mColumnCount = 2;
    private CategoryViewModel categoryViewModel;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private CategoryRecyclerViewAdapter categoryAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        categoryAdapter = new CategoryRecyclerViewAdapter(categoryViewModel.getCategories().getValue());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_nav_category_to_categoryFragmentManager);
        });
        View root = view.findViewById(R.id.category_recycler_view);
        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            recyclerView = (RecyclerView) root;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(categoryAdapter);
            categoryViewModel.getCategories().observe(this, categoryList-> {
                recyclerView.setAdapter(new CategoryRecyclerViewAdapter(categoryList));
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    private void delete(final Category category) {
        categoryViewModel.removeCategory(category);
    }
}
