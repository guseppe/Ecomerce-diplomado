package com.example.ecomerce_diplomado.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecomerce_diplomado.data.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<List<Category>> mCategories;

    public CategoryViewModel() {
        mCategories = new MutableLiveData<>();
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            categoryList.add(new Category(i,"Category"+i, "ic_menu_camera"));
        }
        mCategories.setValue(categoryList);
    }

    public LiveData<List<Category>> getCategories() {
        return mCategories;
    }

    public void addCategory(){
        mCategories.getValue().add(new Category(4,"Category4", "ic_menu_camera"));
        mCategories.postValue(mCategories.getValue());
    }
}
