package com.example.ecomerce_diplomado.ui.category;


import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;
import com.example.ecomerce_diplomado.listener.OnItemTouchListener;
import com.example.ecomerce_diplomado.listener.OptionsMenuListener;
import com.example.ecomerce_diplomado.services.FirebaseService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class CategoryFragment extends Fragment {
    private int mColumnCount = 2;
    private CategoryViewModel categoryViewModel;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private CategoryRecyclerViewAdapter categoryAdapter;
    private Context context;
    private static final String CATEGORY = "CATEGORY";

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        categoryAdapter = new CategoryRecyclerViewAdapter(categoryViewModel.getCategories().getValue());
        context = getContext();
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
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                mColumnCount = 3;
            }
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(categoryAdapter);
            categoryViewModel.getCategories().observe(this, categoryList-> {
                categoryAdapter.refreshCategoryList(categoryList);
                recyclerView.setAdapter(categoryAdapter);
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        categoryAdapter.setOnItemTouchListener(new OnItemTouchListener<Category>() {
            @Override
            public void onClick(Category element) {
                /*Snackbar.make(getView(),  element.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                ImageView imageView = getView().findViewById(R.id.avatar);
                Matrix matrix = new Matrix();
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                matrix.postRotate((float) 90, imageView.getPivotX(), imageView.getPivotY());
                imageView.setImageMatrix(matrix);
            }
        });
         categoryAdapter.setOptionsMenuListener(new OptionsMenuListener<Category>() {

             @Override
             public void onCreateOptionsMenu(View view, final Category element, int position) {
                 PopupMenu popup = new PopupMenu(context, view);
                 popup.inflate(R.menu.action_menu);
                 popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                     @Override
                     public boolean onMenuItemClick(MenuItem item) {
                             switch (item.getItemId()) {
                                 case R.id.action_manager:
                                     Bundle bundle = new Bundle();
                                     bundle.putParcelable(CATEGORY, element);
                                     Navigation.findNavController(view).navigate(R.id.action_nav_category_to_categoryFragmentManager,bundle);
                                     return true;
                                 case R.id.action_delete:
                                     delete(element);
                                     return true;
                                 default:
                                     return false;
                             }
                     }
                 });
                 popup.show();
             }
         });
    }



    private void delete(final Category category) {
        AlertDialog.Builder confirmation = new AlertDialog.Builder(context);
                confirmation.setTitle("Confirmar eliminación")
                .setMessage("Estas a punto de eliminar un registro, deseas continuar?")
                .setPositiveButton("Si",(dialog,id)->{
                    FirebaseService.obtain().delete(category.getPhoto(),response->{
                        categoryViewModel.removeCategory(category);
                    },error->{
                        Toast.makeText(context,error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                .setNegativeButton("No",(dialog, id) -> {
                    dialog.cancel();
                })
                .show();
    }
}
