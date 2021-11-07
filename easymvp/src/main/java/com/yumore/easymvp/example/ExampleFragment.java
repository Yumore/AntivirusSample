package com.yumore.easymvp.example;

import android.util.Log;

import com.yumore.easymvp.R;
import com.yumore.easymvp.base.BaseMvpFragment;
import com.yumore.easymvp.example.login.LoginPresenter;
import com.yumore.easymvp.example.login.LoginView;
import com.yumore.easymvp.example.register.RegisterPresenter;
import com.yumore.easymvp.example.register.RegisterView;
import com.yumore.easymvp.mvp.CreatePresenter;
import com.yumore.easymvp.mvp.PresenterVariable;

/**
 * @author nathaniel
 */
@CreatePresenter(presenter = {LoginPresenter.class, RegisterPresenter.class})
public class ExampleFragment extends BaseMvpFragment implements LoginView, RegisterView {

    @PresenterVariable
    private LoginPresenter mLoginPresenter;
    @PresenterVariable
    private RegisterPresenter mRegisterPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mainmvp;
    }

    @Override
    protected void init() {
        mLoginPresenter.login();
        mRegisterPresenter.register();
    }

    @Override
    public void loginSuccess() {
        Log.i("ExampleFragment", "登陆成功");
    }

    @Override
    public void registerSuccess() {
        Log.i("ExampleFragment", "注册成功");
    }
}
