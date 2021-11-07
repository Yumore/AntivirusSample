package com.yumore.easymvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumore.easymvp.mvp.BaseMvpView;
import com.yumore.easymvp.mvp.BasePresenter;
import com.yumore.easymvp.mvp.PresenterDispatch;
import com.yumore.easymvp.mvp.PresenterProviders;

/**
 * @author nathaniel
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment implements BaseMvpView {
    protected View rootView;
    protected LayoutInflater inflater;
    protected boolean prepared;
    protected boolean visible;

    protected Context context;
    protected Activity activity;

    private PresenterProviders presenterProviders;
    private PresenterDispatch presenterDispatch;

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (Activity) context;
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getLayoutId(), container, false);
            activity = getActivity();
            context = activity;
            this.inflater = inflater;
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenterProviders = PresenterProviders.inject(this);
        presenterDispatch = new PresenterDispatch(presenterProviders);

        presenterDispatch.attachView(getActivity(), this);
        presenterDispatch.onCreatePresenter(savedInstanceState);
        prepared = true;
        init();
        lazyLoad();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterDispatch.onSaveInstanceState(outState);
    }

    protected P getPresenter() {
        return presenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return presenterProviders;
    }

    /**
     * 获取布局
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    /**
     * 初始化
     */
    protected abstract void init();

    public View findViewById(@IdRes int id) {
        View view;
        if (rootView != null) {
            view = rootView.findViewById(id);
            return view;
        }
        return null;
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!prepared || !visible) {
            return;
        }
        lazyLoadData();
        prepared = false;
    }

    /**
     * 懒加载
     */
    protected void lazyLoadData() {

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            visible = true;
            onVisible();
        } else {
            visible = false;
            onInvisible();
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        presenterDispatch.detachView();
    }

    @Override
    public void onDetach() {
        this.activity = null;
        super.onDetach();
    }
}
