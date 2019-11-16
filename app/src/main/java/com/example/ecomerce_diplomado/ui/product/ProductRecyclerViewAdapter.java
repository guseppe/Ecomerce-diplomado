package com.example.ecomerce_diplomado.ui.product;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomerce_diplomado.R;
import com.example.ecomerce_diplomado.data.model.Product;
import com.example.ecomerce_diplomado.listener.OptionsMenuListener;

import org.w3c.dom.Text;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    private List<Product> _productList;
    private OptionsMenuListener optionsMenuListener;

    public ProductRecyclerViewAdapter(List<Product> productList){
        _productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(_productList.get(position).getProductName());
        holder.avatar.setImageURI(Uri.parse("android.resource://com.example.ecomerce_diplomado/drawable/"+_productList.get(position).getPhoto()));
        holder.productCode.setText(_productList.get(position).getProductCode());
        holder.price.setText(String.valueOf(_productList.get(position).getPrice()));
        final Product element = _productList.get(position);
        holder.config.setOnClickListener(v->{
            if(optionsMenuListener != null){
                optionsMenuListener.onCreateOptionsMenu(holder.config,element,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _productList.size();
    }

    public void refreshProductList(List<Product> productList) {
        _productList = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView avatar;
        TextView productCode;
        TextView price;
        ImageView config;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productCode = itemView.findViewById(R.id.productCode);
            avatar = itemView.findViewById(R.id.avatar);
            price = itemView.findViewById(R.id.price);
            config = itemView.findViewById(R.id.config);
        }
    }

    public void setOptionsMenuListener(OptionsMenuListener optionsMenuListener) {
        this.optionsMenuListener = optionsMenuListener;
    }
}
