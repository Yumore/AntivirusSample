package com.nathaniel.sample.surface;

import static com.nathaniel.utility.PermissionUtils.PERMISSION_SETTING_FOR_RESULT;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hjq.toast.ToastUtils;
import com.hjq.toast.dtoast.CustomToastUtils;
import com.nathaniel.baseui.surface.BaseActivity;
import com.nathaniel.baseui.widget.CustomDialog;
import com.nathaniel.sample.R;
import com.nathaniel.sample.databinding.ActivityNavigateBinding;
import com.nathaniel.sample.toast.ToastActivity;
import com.nathaniel.sample.utility.EventConstants;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.entity.EventMessage;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;


/**
 * @author nathaniel
 */
public class NavigateActivity extends BaseActivity<ActivityNavigateBinding> implements View.OnClickListener {
    private static final int REQUEST_CODE_STORAGE = 0x1001;
    private static final String TAG = NavigateActivity.class.getSimpleName();

    @Override
    public void loadData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void bindView() {
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setText(R.string.app_name);
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        viewBinding.btnPackage.setOnClickListener(this);
        viewBinding.btnText.setOnClickListener(this);
        viewBinding.btnScanner.setOnClickListener(this);
        viewBinding.btnAntivirus.setOnClickListener(this);
        viewBinding.btnToast.setOnClickListener(this);
        viewBinding.btnSetting.setOnClickListener(this);
        viewBinding.btnSetting2.setOnClickListener(this);
        viewBinding.kotlinCoroutines.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        RxPermissions permissions = new RxPermissions(getActivity());
        if (view.getId() == R.id.btn_package) {
            permissions.setLogging(true);
            permissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {
                        startActivity(new Intent(getActivity(), PackageActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "没有权限将无法获取单个app的流量信息", Toast.LENGTH_SHORT).show();
                    }
                });
        } else if (view.getId() == R.id.btn_text) {
            startActivity(new Intent(getActivity(), TextViewActivity.class));
        } else if (view.getId() == R.id.btn_scanner) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    startActivity(new Intent(getActivity(), ScannerActivity.class));
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, REQUEST_CODE_STORAGE);
                }
            } else {
                permissions.setLogging(true);
                permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(this::gotoScanner);
            }
        } else if (view.getId() == R.id.btn_antivirus) {
            startActivity(new Intent(getActivity(), AntivirusActivity.class));
        } else if (view.getId() == R.id.btn_toast) {
            startActivity(new Intent(getActivity(), ToastActivity.class));
        } else if (view.getId() == R.id.btn_setting) {
            Uri packageUri = Uri.parse(String.format("package:%s", getPackageName()));
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
            startActivity(intent);

            CustomToastUtils.make(getActivity())
                .setView(View.inflate(getActivity(), R.layout.five_star_toast, null))
                .setText(R.id.tv_content_custom, "======================msg=============================")
                .setGravity(Gravity.CENTER, 0, 0)
                .showLong();
        } else if (view.getId() == R.id.btn_setting2) {
            gotoRomSettings();
        } else if (view.getId() == R.id.kotlin_coroutines) {
            startActivity(new Intent(getActivity(), FileActivity.class));
        }
    }

    private void gotoRomSettings() {
        Intent intent;
        ComponentName componentName;
        switch (Build.MANUFACTURER.toLowerCase(Locale.ROOT)) {
            case "huawei":
                try {
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", getPackageName());
                    componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
                    intent.setComponent(componentName);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    startActivity(getDetailSettingIntent());
                }
                break;
            case "meizu":
                try {
                    intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra("packageName", getPackageName());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    startActivity(getDetailSettingIntent());
                }
                break;
            case "xiaomi":
            case "redmi":
                try { // MIUI 8 9
                    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    localIntent.putExtra("extra_pkgname", getPackageName());
                    startActivityForResult(localIntent, PERMISSION_SETTING_FOR_RESULT);
                } catch (Exception e) {
                    try { // MIUI 5/6/7
                        Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        localIntent.putExtra("extra_pkgname", getPackageName());
                        startActivityForResult(localIntent, PERMISSION_SETTING_FOR_RESULT);
                    } catch (Exception e1) { // 否则跳转到应用详情
                        //startActivityForResult(getAppDetailSettingIntent(), PERMISSION_SETTING_FOR_RESULT);
                        //这里有个问题，进入活动后需要再跳一级活动，就检测不到返回结果
                        startActivity(getDetailSettingIntent());
                    }
                }
                break;
            case "sony":
                try {
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", getPackageName());
                    ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
                    intent.setComponent(comp);
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(getDetailSettingIntent());
                }
                break;
            case "oppo":
                try {
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", getPackageName());
                    componentName = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
                    intent.setComponent(componentName);
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(getDetailSettingIntent());
                }
                break;
            case "letv":
                try {
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", getPackageName());
                    componentName = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
                    intent.setComponent(componentName);
                } catch (Exception e) {
                    startActivity(getDetailSettingIntent());
                }
                break;
            case "qihu360":
                try {
                    intent = new Intent("android.intent.action.MAIN");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", getPackageName());
                    componentName = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
                    intent.setComponent(componentName);
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(getDetailSettingIntent());
                }
                break;
            default:
                startActivity(getDetailSettingIntent());
                break;
        }

        CustomToastUtils.make(getActivity())
            .setView(View.inflate(getActivity(), R.layout.five_star_toast, null))
            .setText(R.id.tv_content_custom, "*_* *_* *_* *_* 你礼貌么，就这样搞我？我权限不要面子的么！！！*_* *_* *_*")
            .setGravity(Gravity.CENTER, 0, 0)
            .showLong();
    }

    private Intent getDetailSettingIntent() {
        final Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return intent;
    }

    /**
     * 不起作用哦
     */
    public void showGuideDialog() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            View customView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.five_star_toast, null);
            CustomDialog.getInstance(getSupportFragmentManager())
                .setTitle("给个五星好评呗")
                .setCustomView(customView, false)
                .setPositiveButton("开心给小星星", view1 -> ToastUtils.show("谢谢大爷"))
                .setNegativeButton("残忍拒绝", view1 -> ToastUtils.show("该死的白嫖党，臭不要脸的"))
                .showDialog();
        }, 5000);
    }

    private void gotoScanner(Boolean granted) {
        if (granted) {
            startActivity(new Intent(getActivity(), ScannerActivity.class));
        } else {
            ToastUtils.show("没有权限将无法获扫描整个磁盘");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LoggerUtils.logger(TAG, requestCode, resultCode, data);
        gotoScanner(Environment.isExternalStorageManager());
    }

    @Override
    protected ActivityNavigateBinding initViewBinding() {
        return ActivityNavigateBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventMessage(EventMessage<?> eventMessage) {
        switch (eventMessage.getAction()) {
            case EventConstants.TASK_FINISH_PACKAGE:
                ToastUtils.show("可以开始扫描是否有病毒应用了");
                break;
            default:
                break;
        }
    }
}
