package com.yumore.easymvp.example;


import android.util.Log;

import com.yumore.easymvp.R;
import com.yumore.easymvp.base.BaseMvpActivity;
import com.yumore.easymvp.example.login.LoginPresenter;
import com.yumore.easymvp.example.login.LoginView;
import com.yumore.easymvp.example.register.RegisterPresenter;
import com.yumore.easymvp.example.register.RegisterView;
import com.yumore.easymvp.mvp.CreatePresenter;

/**
 * 例子2：多个Presenter和使用 getPresenter 方法获取实例
 *
 * @author nathaniel
 */
@CreatePresenter(presenter = {LoginPresenter.class, RegisterPresenter.class})
public class ExampleActivity2 extends BaseMvpActivity implements LoginView, RegisterView {

    private LoginPresenter mLoginPresenter;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mainmvp;
    }

    @Override
    public void initialize() {
        mLoginPresenter = getPresenterProviders().getPresenter(0);
        mRegisterPresenter = getPresenterProviders().getPresenter(1);

        mLoginPresenter.login();
        mRegisterPresenter.register();
    }

    @Override
    public void loginSuccess() {
        Log.i("ExampleActivity1", "登陆成功");
    }

    @Override
    public void registerSuccess() {
        Log.i("ExampleActivity1", "注册成功");
    }
}

