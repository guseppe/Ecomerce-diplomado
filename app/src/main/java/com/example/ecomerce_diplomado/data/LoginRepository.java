package com.example.ecomerce_diplomado.data;

import android.app.Application;

import com.example.ecomerce_diplomado.data.model.LoggedInUser;
import com.example.ecomerce_diplomado.ui.login.LoginActivity;
import com.example.ecomerce_diplomado.utils.usersession.UserSession;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;
    private Application _application;
    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource, Application application) {
        this.dataSource = dataSource;
        _application = application;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource, Application application) {
        if (instance == null) {
            instance = new LoginRepository(dataSource, application);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        UserSession userSession = new UserSession(_application.getApplicationContext());
        userSession.CreateSession(user);
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
