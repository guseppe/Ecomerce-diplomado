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
       /* for (int i = 0; i < 4; i++) {
            categoryList.add(new Category(i,"Category"+i, "ic_menu_camera"));
        }*/
        mCategories.setValue(categoryList);
    }

    public LiveData<List<Category>> getCategories() {
        return mCategories;
    }

    public void addCategory(String name,String photo){
        mCategories.getValue().add(new Category(getNextid(),name, photo));
        mCategories.postValue(mCategories.getValue());
    }
    public void updateCategory(Category category){
        Category ucat = mCategories.getValue().get(category.getId()-1);
        mCategories.getValue().remove(ucat);
        ucat.setName(category.getName());
        mCategories.getValue().add(ucat);
        mCategories.postValue(mCategories.getValue());
    }

    public void removeCategory(Category category){
        mCategories.getValue().remove(category);
        mCategories.postValue(mCategories.getValue());
    }
    private int getNextid(){
        int nextid = 1;
        List<Category> categoryList = mCategories.getValue();
        if(!categoryList.isEmpty()){
            nextid = categoryList.size() + 1;
        }
        return  nextid;
    }
}
