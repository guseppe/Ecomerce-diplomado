package com.example.ecomerce_diplomado.ui.category;


import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Category;
import com.example.ecomerce_diplomado.listener.OnItemTouchListener;
import com.example.ecomerce_diplomado.listener.OptionsMenuListener;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private  List<Category> _categoryList;
    private OptionsMenuListener optionsMenuListener;
    private OnItemTouchListener onItemTouchListener;



    public CategoryRecyclerViewAdapter(List<Category> categoryList) {

        _categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(_categoryList.get(position).getName());
        holder.avatar.setImageURI(Uri.parse("android.resource://com.example.ecomerce_diplomado/drawable/"+_categoryList.get(position).getPhoto()));
        final Category element = _categoryList.get(position);
        holder.action.setOnClickListener(v->{
            if(optionsMenuListener != null){
                optionsMenuListener.onCreateOptionsMenu(holder.action,element,position);
            }
        });

        holder.avatar.setOnClickListener(v->{
                if (onItemTouchListener != null) {
                    onItemTouchListener.onClick(element);
                }
        });

        holder.name.setOnClickListener(v-> {
            Log.e("Click en category name","Clicked");
                if (onItemTouchListener != null) {
                    onItemTouchListener.onClick(element);
                }
        });


    }



    @Override
    public int getItemCount() {
        return _categoryList.size();
    }

    public void refreshCategoryList(List<Category> categoryList) {
        _categoryList = categoryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView name;
        public ImageView action;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            action = itemView.findViewById(R.id.config);
        }
    }

    public void setOptionsMenuListener(OptionsMenuListener optionsMenuListener) {
        this.optionsMenuListener = optionsMenuListener;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }


}
