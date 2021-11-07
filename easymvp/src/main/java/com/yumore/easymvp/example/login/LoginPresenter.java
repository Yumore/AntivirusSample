package com.yumore.easymvp.example.login;


import com.yumore.easymvp.mvp.BasePresenter;

/**
 * @author nathaniel
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public void login() {
        baseView.loginSuccess();
    }
}
