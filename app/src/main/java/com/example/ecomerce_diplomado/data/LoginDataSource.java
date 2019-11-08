package com.example.ecomerce_diplomado.data;

import com.example.ecomerce_diplomado.data.model.LoggedInUser;

import java.io.IOException;

import javax.security.auth.login.LoginException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            if(username.compareTo("guseppe") == 0 && password.compareTo("123456") == 0 ) {
                LoggedInUser User = new LoggedInUser(java.util.UUID.randomUUID().toString(), "Guseppe");
                return new Result.Success<>(User);
            }else if(username.compareTo("guseppe2") == 0 && password.compareTo("123456") == 0 ) {
                LoggedInUser User = new LoggedInUser(java.util.UUID.randomUUID().toString(), "Guseppe2");
                return new Result.Success<>(User);
            }else{
                return  new Result.Error(new LoginException());
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error al iniciar sesion", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
