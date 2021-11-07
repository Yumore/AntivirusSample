package com.yumore.easymvp.example;

import android.util.Log;

import com.yumore.easymvp.R;
import com.yumore.easymvp.base.BaseMvpActivity;
import com.yumore.easymvp.example.login.LoginPresenter;
import com.yumore.easymvp.example.login.LoginView;
import com.yumore.easymvp.mvp.CreatePresenter;

/**
 * 例子3：一个Presenter和使用 getPresenter 方法获取实例
 *
 * @author nathaniel
 */
@CreatePresenter(presenter = LoginPresenter.class)
public class ExampleActivity3 extends BaseMvpActivity<LoginPresenter> implements LoginView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mainmvp;
    }

    @Override
    public void initialize() {
        getPresenter().login();
    }

    @Override
    public void loginSuccess() {
        Log.i("ExampleActivity1", "登陆成功");
    }
}


