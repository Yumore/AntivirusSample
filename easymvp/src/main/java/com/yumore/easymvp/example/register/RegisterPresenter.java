package com.yumore.easymvp.example.register;


import com.yumore.easymvp.mvp.BasePresenter;

/**
 * @author nathaniel
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

    public void register() {
        baseView.registerSuccess();
    }
}
