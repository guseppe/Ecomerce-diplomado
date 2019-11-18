package com.example.ecomerce_diplomado.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.ecomerce_diplomado.R;

public class ProductFragmentManager extends Fragment {
    private ProductViewModel productViewModel;
    private ImageView avatar;
    private TextView productCode;
    private TextView productName;
    private TextView price;
    private Button btnsave;
    private Button btnvolver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_product_manager,container,false);
       avatar = view.findViewById(R.id.avatar);
       productName = view.findViewById(R.id.productName);
       productCode = view.findViewById(R.id.productCode);
       price = view.findViewById(R.id.price);
       btnsave = view.findViewById(R.id.save);
       btnvolver = view.findViewById(R.id.back);
       return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnvolver.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_productFragmentManager_to_nav_product);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
