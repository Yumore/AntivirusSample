package com.yumore.easymvp.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yumore.easymvp.mvp.BaseMvpView;
import com.yumore.easymvp.mvp.BasePresenter;
import com.yumore.easymvp.mvp.PresenterDispatch;
import com.yumore.easymvp.mvp.PresenterProviders;


/**
 * @author nathaniel
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseMvpView {

    private PresenterProviders presenterProviders;
    private PresenterDispatch presenterDispatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenterProviders = PresenterProviders.inject(this);
        presenterDispatch = new PresenterDispatch(presenterProviders);

        presenterDispatch.attachView(this, this);
        presenterDispatch.onCreatePresenter(savedInstanceState);
        initialize();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterDispatch.onSaveInstanceState(outState);
    }

    /**
     * 获取布局文件
     *
     * @return layoutId
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     */
    public abstract void initialize();

    protected P getPresenter() {
        return presenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return presenterProviders;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showProgress(boolean display) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterDispatch.detachView();
    }
}
