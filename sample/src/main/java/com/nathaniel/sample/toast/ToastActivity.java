package com.nathaniel.sample.toast;

import android.content.Intent;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.XToast;
import com.hjq.toast.draggable.MovingDraggable;
import com.hjq.toast.draggable.SpringDraggable;
import com.hjq.toast.style.BlackToastStyle;
import com.hjq.toast.style.WhiteToastStyle;
import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.sample.databinding.ActivityToastBinding;
import com.nathaniel.utility.ThreadManager;

import java.util.List;

/**
 * @author Nathaniel
 */
public final class ToastActivity extends AbstractActivity<ActivityToastBinding> implements View.OnClickListener {
    @Override
    protected ActivityToastBinding initViewBinding() {
        return ActivityToastBinding.inflate(getLayoutInflater());
    }

    public void show11(View v) {
        ToastUtils.show("我是普通的 Toast");
    }

    public void show12(View v) {
        ThreadManager.getInstance().executor(() -> ToastUtils.show("我是子线程中弹出的吐司"));
    }

    public void show13(View v) {
        ToastUtils.setStyle(new WhiteToastStyle());
        ToastUtils.show("动态切换白色吐司样式成功");
    }

    public void show14(View v) {
        ToastUtils.setStyle(new BlackToastStyle());
        ToastUtils.show("动态切换黑色吐司样式成功");
    }

    public void show15(View v) {
        ToastUtils.setView(R.layout.toast_custom_view);
        ToastUtils.setGravity(Gravity.CENTER);
        ToastUtils.show("自定义 Toast 布局");
    }

    public void show16(View view) {
        if (XXPermissions.isGranted(this, Permission.NOTIFICATION_SERVICE)) {
            Snackbar.make(getWindow().getDecorView(), "正在准备跳转到手机桌面，请注意有极少数机型无法在后台显示 Toast", Snackbar.LENGTH_SHORT).show();
            view.postDelayed(() -> {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }, 2000);
            view.postDelayed(() -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ToastUtils.show("我是在后台显示的 Toast（Android 11 及以上在后台显示 Toast 只能使用系统样式）");
                } else {
                    ToastUtils.show("我是在后台显示的 Toast");
                }
            }, 3000);
        } else {
            ToastUtils.show("在后台显示 Toast 需要先获取通知栏权限");
            view.postDelayed(() -> XXPermissions.startPermissionActivity(ToastActivity.this, Permission.NOTIFICATION_SERVICE), 2000);
        }
    }

    public void show17(View v) {
        new XToast<>(ToastActivity.this)
            .setDuration(1000)
            .setContentView(ToastUtils.getStyle().createView(getApplication()))
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setText(android.R.id.message, "就问你溜不溜")
            .setGravity(Gravity.BOTTOM)
            .setYOffset((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()))
            .show();
    }

    public void show21(View v) {
        new XToast<>(this)
            .setDuration(3000)
            .setContentView(R.layout.toast_hint)
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_finish)
            .setText(android.R.id.message, "这个动画是不是很骚")
            .show();
    }

    public void show22(View v) {
        new XToast<>(this)
            .setDuration(1000)
            .setContentView(R.layout.toast_hint)
            .setAnimStyle(android.R.style.Animation_Activity)
            .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_error)
            .setText(android.R.id.message, "一秒后消失")
            .show();
    }

    public void show23(View v) {
        new XToast<>(this)
            .setDuration(3000)
            .setContentView(R.layout.toast_hint)
            .setAnimStyle(android.R.style.Animation_Dialog)
            .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_warning)
            .setText(android.R.id.message, "是不是感觉很牛逼")
            .setOnToastListener(new XToast.OnToastListener() {

                @Override
                public void onShow(XToast<?> toast) {
                    Snackbar.make(getWindow().getDecorView(), "XToast 显示了", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onDismiss(XToast<?> toast) {
                    Snackbar.make(getWindow().getDecorView(), "XToast 消失了", Snackbar.LENGTH_SHORT).show();
                }
            })
            .show();
    }

    public void show24(View v) {
        new XToast<>(this)
            .setContentView(R.layout.toast_hint)
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_finish)
            .setText(android.R.id.message, "点我点我点我")
            .setOnClickListener(android.R.id.message, (XToast.OnClickListener<TextView>) (toast, view) -> {
                view.setText("那么听话啊");
                toast.postDelayed(toast::cancel, 1000);
            })
            .show();
    }

    public void show25(View v) {
        new XToast<>(this)
            .setContentView(R.layout.toast_hint)
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_finish)
            .setDuration(2000)
            .setText(android.R.id.message, "位置算的准不准")
            .setOnClickListener(android.R.id.message, (XToast.OnClickListener<TextView>) (toast, view) -> toast.cancel())
            .showAsDropDown(v, Gravity.BOTTOM);
    }

    public void show26(View v) {
        new XToast<>(this)
            .setContentView(R.layout.toast_hint)
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_finish)
            .setText(android.R.id.message, "点我消失")
            // 设置外层是否能被触摸
            .setOutsideTouchable(false)
            // 设置窗口背景阴影强度
            .setBackgroundDimAmount(0.5f)
            // 设置成可拖拽的
            .setDraggable(new MovingDraggable())
            .setOnClickListener(android.R.id.message, (XToast.OnClickListener<TextView>) (toast, view) -> toast.cancel())
            .show();
    }

    public void show27(View v) {
        XXPermissions.with(this)
            .permission(Permission.SYSTEM_ALERT_WINDOW)
            .request(new OnPermissionCallback() {

                @Override
                public void onGranted(List<String> granted, boolean all) {
                    // 传入 Application 表示这个是一个全局的 Toast
                    new XToast<>(getApplication())
                        .setContentView(R.layout.toast_phone)
                        .setGravity(Gravity.END | Gravity.BOTTOM)
                        .setYOffset(200)
                        // 设置指定的拖拽规则
                        .setDraggable(new SpringDraggable())
                        .setOnClickListener(android.R.id.icon, (XToast.OnClickListener<ImageView>) (toast, view) -> {
                            ToastUtils.show("我被点击了");
                            // 点击后跳转到拨打电话界面
                            // Intent intent = new Intent(Intent.ACTION_DIAL);
                            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // toast.startActivity(intent);
                            // 安卓 10 在后台跳转 Activity 需要额外适配
                            // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
                        })
                        .setOnLongClickListener(android.R.id.icon, (toast, view) -> {
                            ToastUtils.show("我被长按了");
                            return true;
                        })
                        .show();
                }

                @Override
                public void onDenied(List<String> denied, boolean never) {
                    new XToast<>(ToastActivity.this)
                        .setDuration(1000)
                        .setContentView(R.layout.toast_hint)
                        .setImageDrawable(android.R.id.icon, R.drawable.ic_dialog_tip_error)
                        .setText(android.R.id.message, "请先授予悬浮窗权限")
                        .show();
                }
            });
    }

    public void show28(View v) {
        // 将 ToastUtils 中的 View 转移给 XToast 来显示
        new XToast<>(this)
            .setDuration(1000)
            .setContentView(ToastUtils.getStyle().createView(this))
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setText(android.R.id.message, "就问你溜不溜")
            .setGravity(Gravity.BOTTOM)
            .setYOffset(100)
            .show();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void bindView() {
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setVisibility(View.VISIBLE);
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setOnClickListener(this);
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setText("Toast演示");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.common_header_back_iv) {
            finish();
        }
    }
}