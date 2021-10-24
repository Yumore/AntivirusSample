package com.nathaniel.sample.surface;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.utility.LoggerUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author nathaniel
 */
public class NavigateActivity extends AbstractActivity {
    private static final int REQUEST_CODE_STORAGE = 0x1001;
    private static final String TAG = NavigateActivity.class.getSimpleName();
    @BindView(R.id.btn_package)
    Button btnPackage;
    @BindView(R.id.common_header_title_tv)
    TextView commonHeaderTitleTv;
    @BindView(R.id.common_header_root)
    RelativeLayout commonHeaderRoot;
    @BindView(R.id.btn_text)
    Button btnText;
    @BindView(R.id.btn_browser)
    Button btnNavigate;
    @BindView(R.id.btn_scanner)
    Button btnScanner;
    @BindView(R.id.btn_antivirus)
    Button btnAntivirus;

    @Override
    public int getLayoutId() {
        return R.layout.activity_navigate;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void bindView() {
        commonHeaderTitleTv.setText(R.string.app_name);
        commonHeaderTitleTv.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) commonHeaderTitleTv.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    }


    @OnClick({
        R.id.btn_package,
        R.id.btn_browser,
        R.id.btn_text,
        R.id.btn_antivirus,
        R.id.btn_scanner
    })
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
            case R.id.btn_browser:
                startActivity(new Intent(getActivity(), BrowserActivity.class));
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
            Toast.makeText(getActivity(), "没有权限将无法获扫描整个磁盘", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LoggerUtils.logger(TAG, requestCode, resultCode, data);
        gotoScanner(Environment.isExternalStorageManager());
    }
}
