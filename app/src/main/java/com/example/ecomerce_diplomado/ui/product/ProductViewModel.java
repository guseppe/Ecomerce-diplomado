package com.example.ecomerce_diplomado.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecomerce_diplomado.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<Product>> mProducts;

    public ProductViewModel() {
        mProducts = new MutableLiveData<List<Product>>();
        List<Product> productList = new ArrayList<Product>();
         /*for (int i = 1; i < 5; i++) {
            productList.add(new Product(i,"product"+i,"ic_menu_product", "code"+i,1500.00));
         }*/
         mProducts.setValue(productList);
    }
    public LiveData<List<Product>> getProducts() {
        return mProducts;
    }

    public void addProduct(String name,String photo, String productCode, Double price, int categoryId){
        mProducts.getValue().add(new Product(getNextid(),name, photo,productCode,price,categoryId));
        mProducts.postValue(mProducts.getValue());
    }
    public void updateProductNotify(){
        mProducts.postValue(mProducts.getValue());
    }

    public void removeProduct(Product product){
        mProducts.getValue().remove(product);
        mProducts.postValue(mProducts.getValue());
    }

    private int getNextid(){
        int nextid = 1;
        List<Product> productList = mProducts.getValue();
        if(!productList.isEmpty()){
            nextid = productList.size() + 1;
        }
        return  nextid;
    }
}
