package com.example.ecomerce_diplomado.listener;

import android.view.View;

public interface OptionsMenuListener<T> {
    public void onCreateOptionsMenu(View view, T element, int position);
}
