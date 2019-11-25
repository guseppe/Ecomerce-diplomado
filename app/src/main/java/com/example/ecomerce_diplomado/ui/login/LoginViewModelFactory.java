package com.example.ecomerce_diplomado.ui.login;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.ecomerce_diplomado.data.LoginDataSource;
import com.example.ecomerce_diplomado.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private  Application _application;

    public LoginViewModelFactory(@NonNull Application application) {
        super(application);
        _application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(_application, LoginRepository.getInstance(new LoginDataSource(),_application));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
