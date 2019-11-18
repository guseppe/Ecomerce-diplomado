package com.example.ecomerce_diplomado.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;
import com.example.ecomerce_diplomado.data.model.Product;
import com.example.ecomerce_diplomado.ui.category.CategoryViewModel;


public class ProductFragmentManager extends Fragment {
    private ProductViewModel productViewModel;
    private ImageView avatar;
    private TextView productCode;
    private TextView productName;
    private TextView price;
    private Button btnsave;
    private Button btnvolver;
    private Product element;
    private Spinner spinCategory;
    private CategoryViewModel categoryViewModel;
    private static final String PRODUCT = "PRODUCT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
        categoryViewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        Bundle bundle = getArguments();
        if(bundle != null){
            element = bundle.getParcelable(PRODUCT);
        }
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
       spinCategory = view.findViewById(R.id.category);
       return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<Category> spinAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,categoryViewModel.getCategories().getValue());
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(spinAdapter);
        if(element != null){
            int spinnerPosition = spinAdapter.getPosition(new Category(element.getCategoryId()));
            spinCategory.setSelection(spinnerPosition);
            productName.setText(element.getProductName());
            productCode.setText(element.getProductCode());
            price.setText(String.valueOf(element.getPrice()));
        }
        btnvolver.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_productFragmentManager_to_nav_product);
        });
        btnsave.setOnClickListener(v->{
            if(element != null){
                element.setProductName(productName.getText().toString());
                element.setProductCode(productCode.getText().toString());
                element.setPrice(Double.valueOf(price.getText().toString()));
                productViewModel.updateProductNotify();
            }
            else{
                productViewModel.addProduct(productName.getText().toString(),"ic_menu_product",productCode.getText().toString(),Double.valueOf(price.getText().toString()),spinCategory.getSelectedItemPosition());
            }
            Navigation.findNavController(view).navigate(R.id.action_productFragmentManager_to_nav_product);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
