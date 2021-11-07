package com.yumore.easymvp.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author nathaniel
 */
public class PresenterProviders {

    private final PresenterStore presenterStore = new PresenterStore<>();
    private Activity activity;
    private Fragment fragment;
    private Class<?> clazz;

    private PresenterProviders(Activity activity, Fragment fragment) {
        if (activity != null) {
            this.activity = activity;
            clazz = this.activity.getClass();
        }
        if (fragment != null) {
            this.fragment = fragment;
            clazz = this.fragment.getClass();
        }
        resolveCreatePresenter();
        resolvePresenterVariable();
    }

    public static PresenterProviders inject(Activity activity) {
        return new PresenterProviders(activity, null);
    }

    public static PresenterProviders inject(Fragment fragment) {
        return new PresenterProviders(null, fragment);
    }

    private static Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request PresenterProviders before onCreate call.");
        }
        return application;
    }

    private static Activity checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create PresenterProviders for detached fragment");
        }
        return activity;
    }

    private static Context checkContext(Context context) {
        Context resultContent = null;
        if (context instanceof Activity) {
            resultContent = context;
        }
        if (resultContent == null) {
            throw new IllegalStateException("Context must Activity Context");
        }
        return resultContent;
    }

    @SuppressWarnings("unchecked")
    private <P extends BasePresenter> PresenterProviders resolveCreatePresenter() {
        CreatePresenter createPresenter = clazz.getAnnotation(CreatePresenter.class);
        if (createPresenter != null) {

            Class<P>[] classes = (Class<P>[]) createPresenter.presenter();
            for (Class<P> clazz : classes) {
                String canonicalName = clazz.getCanonicalName();
                try {
                    presenterStore.put(canonicalName, clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private <P extends BasePresenter> PresenterProviders resolvePresenterVariable() {
        for (Field field : clazz.getDeclaredFields()) {
            //获取字段上的注解
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length < 1) {
                continue;
            }
            if (annotations[0] instanceof PresenterVariable) {
                String canonicalName = field.getType().getName();
                P presenterInstance = (P) presenterStore.get(canonicalName);
                if (presenterInstance != null) {
                    try {
                        field.setAccessible(true);
                        field.set(fragment != null ? fragment : activity, presenterInstance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }


    @SuppressWarnings("unchecked")
    public <P extends BasePresenter> P getPresenter(int index) {
        CreatePresenter createPresenter = clazz.getAnnotation(CreatePresenter.class);
        if (createPresenter == null) {
            return null;
        }
        if (createPresenter.presenter().length == 0) {
            return null;
        }
        if (index >= 0 && index < createPresenter.presenter().length) {
            String key = createPresenter.presenter()[index].getCanonicalName();
            BasePresenter presenter = presenterStore.get(key);
            if (presenter != null) {
                return (P) presenter;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public PresenterStore<BasePresenter> getPresenterStore() {
        return presenterStore;
    }
}
