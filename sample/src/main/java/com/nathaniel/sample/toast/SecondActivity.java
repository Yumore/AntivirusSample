package com.nathaniel.sample.toast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.toast.dtoast.CustomToastUtils;
import com.nathaniel.sample.R;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isDestroyed() || isFinishing()) {
                    return;
                }
                ToastUtil.show(SecondActivity.this, "Hello SecondActivity!");
            }
        }, 500);
    }


    @Override
    public void onClick(View v) {
        ToastUtil.show(this, "我就是来搞笑的");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除与{@param mActivity}关联的ActivityToast，避免窗口泄漏
        //如果DToast.make(mContext)使用的是ActivityContext,则在退出Activity时需要调用
        CustomToastUtils.cancelActivityToast(this);
    }
}
