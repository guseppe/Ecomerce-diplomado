package com.example.ecomerce_diplomado.ui.category;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryFragment extends Fragment {
    private int mColumnCount = 2;
    private CategoryViewModel categoryViewModel;
    private FloatingActionButton fab;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        categoryViewModel =
                ViewModelProviders.of(this).get(CategoryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 categoryViewModel.addCategory();
            }
        });
        /*final TextView textView = root.findViewById(R.id.text_category);
        categoryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/  //este bloque se podria utilizar para refrescar la lista
        View root = view.findViewById(R.id.category_recycler_view);
        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            final RecyclerView recyclerView = (RecyclerView) root;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new CategoryRecyclerViewAdapter(categoryViewModel.getCategories().getValue()));

        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel
        View root = getView().findViewById(R.id.category_recycler_view);
        final RecyclerView recyclerView = (RecyclerView) root;
        categoryViewModel.getCategories().observe(this, categoryList-> {
            recyclerView.setAdapter(new CategoryRecyclerViewAdapter(categoryList));
        });
    }


}
