package com.nathaniel.sample.surface;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.baseui.utility.ItemDecoration;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.PackageAdapter;
import com.nathaniel.sample.databinding.ActivityPackageBinding;
import com.nathaniel.sample.utility.AppUtils;
import com.nathaniel.sample.utility.DataUtils;
import com.nathaniel.utility.AbstractTask;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.ThreadManager;
import com.nathaniel.utility.entity.PackageEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/29/21 - 7:33 PM
 */
public class PackageActivity extends AbstractActivity<ActivityPackageBinding> implements TextWatcher, TextView.OnEditorActionListener {
    private static final int HANDLER_WHAT_REFRESH = 0x0101;
    private static final long DELAY_MILLIS = 1000L;
    private static final String TAG = PackageActivity.class.getSimpleName();
    private final String regex = "yyyy-MM-dd HH:mm:ss";
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
    /**
     * 刷新页面信息的handler
     */
    private Handler handler;
    private PackageAdapter packageAdapter;
    private List<PackageEntity> packageEntityList, originalPackageEntityList;
    private View emptyLayout;
    private boolean cacheEnable;

    @SuppressLint("DefaultLocale")
    @Override
    public void loadData() {
        packageEntityList = AppUtils.queryPackageList();
        cacheEnable = EmptyUtils.isEmpty(packageEntityList);
        originalPackageEntityList = new ArrayList<>();
        handler = new Handler(getMainLooper()) {
            @SuppressLint("DefaultLocale")
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == HANDLER_WHAT_REFRESH) {
                    bindData2Summary();
                    new Handler(getMainLooper()).postDelayed(() -> {
                        handler.sendEmptyMessage(HANDLER_WHAT_REFRESH);
                    }, DELAY_MILLIS);
                } else {
                    super.handleMessage(msg);
                }
            }
        };
        packageAdapter = new PackageAdapter(R.layout.item_package_recycler_list, packageEntityList);
        emptyLayout = getLayoutInflater().inflate(R.layout.common_empty_layout, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstTime) {
            return;
        }
        if (AppUtils.accessPermission(getApplicationContext())) {
            scannerPackage();
        } else {
            TextView textView = obtainView(emptyLayout, R.id.common_empty_message);
            textView.setText("如果需要获取所有App的流量信息，需要手动授予权限才可以");
            textView.setVisibility(View.VISIBLE);
            packageAdapter.setEmptyView(emptyLayout);
            LoggerUtils.logger(TAG, "READ_PRIVILEGED_PHONE_STATE 无法获取单独App的流量信息");
        }
        firstTime = false;
    }


    private void bindData2Summary() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            viewBinding.packageTotalTv.setText(String.format("当前系统版本过低，部分数据无法同居\n开机到现在：%s ,\n 本月流量(暂未统计),\n 当前时间：%s",
                DataUtils.getRealDataSize(TrafficStats.getMobileTxBytes() + TrafficStats.getMobileRxBytes()),
                simpleDateFormat.format(System.currentTimeMillis())));
        } else {
            viewBinding.packageTotalTv.setText(String.format("开机到现在(移动数据 / WiFi流量 / 流量总和)：%s / %s / %s,\n 本月流量(移动数据 / WiFi流量 / 流量总和)：%s / %s / %s,\n 当前时间：%s",
                DataUtils.getRealDataSize(TrafficStats.getMobileTxBytes()),
                DataUtils.getRealDataSize(TrafficStats.getTotalRxBytes() - TrafficStats.getMobileTxBytes()),
                DataUtils.getRealDataSize(TrafficStats.getTotalRxBytes()),
                DataUtils.getRealDataSize(TrafficStats.getMobileRxBytes()),
                DataUtils.getRealDataSize(TrafficStats.getTotalRxBytes() - TrafficStats.getMobileTxBytes()),
                DataUtils.getRealDataSize(TrafficStats.getTotalRxBytes()),
                simpleDateFormat.format(System.currentTimeMillis())));
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void bindView() {
        bindData2Summary();
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setVisibility(View.VISIBLE);
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setText("流量排行");
        viewBinding.commonHeaderRootLayout.commonHeaderOptionTv.setText("运营商设置");
        viewBinding.commonHeaderRootLayout.commonHeaderOptionTv.setVisibility(View.VISIBLE);
        viewBinding.commonHeaderRootLayout.commonHeaderOptionTv.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
            startActivity(intent);
        });
        viewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        int padding = (int) getResources().getDimension(R.dimen.common_padding_small);
        viewBinding.recyclerView.addItemDecoration(new ItemDecoration(padding, ItemDecoration.LINEAR_LAYOUT_MANAGER));
        viewBinding.recyclerView.setAdapter(packageAdapter);
        viewBinding.packageSearchEt.addTextChangedListener(this);
        viewBinding.packageSearchEt.setOnEditorActionListener(this);
        if (AppUtils.accessPermission(getApplicationContext())) {
            scannerPackage();
        } else {
            // 打开“有权查看使用情况的应用”页面
            new AlertDialog.Builder(getActivity())
                .setMessage("如果需要获取所有App的流量信息，需要手动授予权限才可以")
                .setPositiveButton("授予", (dialog, which) -> {
                    try {
                        Uri uri = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        LoggerUtils.logger(TAG, uri.toString());
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.settings.USAGE_ACCESS_SETTINGS
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        LoggerUtils.logger(TAG, uri.toString());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("我再想想", (dialog, which) -> {
                    TextView textView = obtainView(emptyLayout, R.id.common_empty_message);
                    textView.setText("如果需要获取所有App的流量信息，需要手动授予权限才可以");
                    textView.setVisibility(View.VISIBLE);
                    packageAdapter.setEmptyView(emptyLayout);
                })
                .create()
                .show();
        }
    }

    private void scannerPackage() {
        LoggerUtils.logger(TAG, "READ_PRIVILEGED_PHONE_STATE 已經拿到");
        handler.sendEmptyMessage(HANDLER_WHAT_REFRESH);
        ThreadManager.getInstance().executor(new AbstractTask<List<PackageEntity>>() {
            @Override
            public void prepareRunnable() {
                super.prepareRunnable();
                showLoading("因扫描所有流量信息比较耗费时间，且需要分开统计，所以时间会比较长，请耐心等待......", cacheEnable);
            }

            @Override
            public void runnableCallback(List<PackageEntity> packageEntities) {
                dismissLoading();
                if (cacheEnable) {
                    Toast.makeText(getActivity(), "更新完成", Toast.LENGTH_SHORT).show();
                }
                if (!EmptyUtils.isEmpty(packageEntityList)) {
                    packageEntityList.clear();
                }
                packageEntityList.addAll(packageEntities);
                originalPackageEntityList.addAll(packageEntities);
                packageAdapter.notifyDataSetChanged();
            }

            @Override
            protected List<PackageEntity> doRunnableCode() {
                return AppUtils.getPackageEntities(getActivity());
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (EmptyUtils.isEmpty(charSequence)) {
            if (!EmptyUtils.isEmpty(packageEntityList)) {
                packageEntityList.clear();
            }
            packageEntityList.addAll(originalPackageEntityList);
            LoggerUtils.logger(TAG, "originalPackageEntityList.size = " + originalPackageEntityList.size());
            packageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (EmptyUtils.isEmpty(view.getText())) {
            return false;
        }
        if (view.getId() == viewBinding.packageSearchEt.getId() && actionId == EditorInfo.IME_ACTION_SEARCH) {
            List<PackageEntity> packageEntities = new ArrayList<>();
            for (PackageEntity packageEntity : packageEntityList) {
                if (packageEntity.getAppName().contains(viewBinding.packageSearchEt.getText().toString())) {
                    packageEntities.add(packageEntity);
                }
            }
            if (!EmptyUtils.isEmpty(packageEntityList)) {
                packageEntityList.clear();
            }
            packageEntityList.addAll(packageEntities);
            packageAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    protected ActivityPackageBinding initViewBinding() {
        return ActivityPackageBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(HANDLER_WHAT_REFRESH);
        super.onDestroy();
    }
}
