package com.nathaniel.sample.surface;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hjq.toast.ToastUtils;
import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.sample.databinding.ActivityNavigateBinding;
import com.nathaniel.sample.utility.EventConstants;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.entity.EventMessage;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author nathaniel
 */
public class NavigateActivity extends AbstractActivity<ActivityNavigateBinding> implements View.OnClickListener {
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
    }

    @Override
    public void onClick(View view) {
        RxPermissions permissions = new RxPermissions(getActivity());
        switch (view.getId()) {
            case R.id.btn_package:
                permissions.setLogging(true);
                permissions.request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(granted -> {
                        if (granted) {
                            startActivity(new Intent(getActivity(), PackageActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "没有权限将无法获取单个app的流量信息", Toast.LENGTH_SHORT).show();
                        }
                    });
                break;
            case R.id.btn_text:
                startActivity(new Intent(getActivity(), TextViewActivity.class));
                break;
            case R.id.btn_scanner:
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
                break;
            case R.id.btn_antivirus:
                startActivity(new Intent(getActivity(), AntivirusActivity.class));
                break;
            default:
                break;
        }
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
