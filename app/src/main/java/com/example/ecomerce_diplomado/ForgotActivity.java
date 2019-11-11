package com.example.ecomerce_diplomado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecomerce_diplomado.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        final TextView btnregister = findViewById(R.id.btnregister);
        final TextView btnlogin = findViewById(R.id.btnlogin);
        final Button btnsend = findViewById(R.id.btnsend);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "123456", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
