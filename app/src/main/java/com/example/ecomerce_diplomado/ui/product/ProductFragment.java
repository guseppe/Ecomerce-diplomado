package com.example.ecomerce_diplomado.ui.product;

import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
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

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Product;
import com.example.ecomerce_diplomado.listener.OptionsMenuListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductFragment extends Fragment {

    private ProductViewModel productViewModel;
    private ProductRecyclerViewAdapter productAdapter;
    private Context context;
    private FloatingActionButton fab;
    private final String PRODUCT = "PRODUCT";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
        productAdapter = new ProductRecyclerViewAdapter(productViewModel.getProducts().getValue());
        context = getContext();
    }

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_nav_product_to_productFragmentManager);
        });
        View root = view.findViewById(R.id.product_recycler_view);
        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            RecyclerView recyclerView = (RecyclerView) root;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
           /* recyclerView.setHasFixedSize(true); //para que no se descuadre cuando hay muchos datos, pendiente probar
            recyclerView.setItemAnimator(new DefaultItemAnimator()); //se desaparece animacion por defecto*/
            recyclerView.setAdapter(productAdapter);
            productViewModel.getProducts().observe(this, productList-> {
                productAdapter.refreshProductList(productList);
                recyclerView.setAdapter(productAdapter);
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        productAdapter.setOptionsMenuListener(new OptionsMenuListener<Product>() {

            @Override
            public void onCreateOptionsMenu(View view, final Product element, int position) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.inflate(R.menu.action_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_manager:
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(PRODUCT, element);
                                Navigation.findNavController(view).navigate(R.id.action_nav_product_to_productFragmentManager,bundle);
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

    private void delete(Product element) {
    }


}
