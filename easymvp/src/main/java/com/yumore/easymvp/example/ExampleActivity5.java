package com.yumore.easymvp.example;

import com.yumore.easymvp.R;
import com.yumore.easymvp.base.BaseMvpActivity;

/**
 * 例子4：Fragment
 *
 * @author nathaniel
 */
public class ExampleActivity5 extends BaseMvpActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initialize() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, new ExampleFragment())
                .commit();
    }
}
