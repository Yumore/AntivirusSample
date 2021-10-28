package com.nathaniel.sample.surface;

import android.os.Build;
import android.os.Environment;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hjq.toast.ToastUtils;
import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.ScannerAdapter;
import com.nathaniel.sample.databinding.ActivityScannerBinding;
import com.nathaniel.sample.module.ScannerModel;
import com.nathaniel.sample.utility.CleanManager;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PathEntity;

import java.util.List;


/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/7/30 - 09:56
 */
public class ScannerActivity extends AbstractActivity<ActivityScannerBinding> implements View.OnClickListener {
    private ScannerAdapter scannerAdapter;
    private List<PathEntity> pathEntityList;
    private String rootPath;

    @Override
    public void loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            rootPath = getExternalFilesDir(null).getAbsolutePath();
        } else {
            rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        pathEntityList = SingletonUtils.getSingleton(ScannerModel.class).getRootPaths();
        scannerAdapter = new ScannerAdapter(R.layout.item_scanner_result_list, pathEntityList);
    }

    @Override
    public void bindView() {
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setVisibility(View.VISIBLE);
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setOnClickListener(this);
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setText("全盘文件扫描");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.scannerContainerRv.setLayoutManager(linearLayoutManager);
        viewBinding.scannerContainerRv.setAdapter(scannerAdapter);

        SingletonUtils.getSingleton(CleanManager.class).scannerFiles(rootPath, pathEntities -> {
            pathEntityList.addAll(pathEntities);
            scannerAdapter.notifyDataSetChanged();
            ToastUtils.show("扫描完成");
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.common_header_back_iv) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        SingletonUtils.getSingleton(CleanManager.class).stopScanner();
        super.onDestroy();
    }

    @Override
    protected ActivityScannerBinding initViewBinding() {
        return ActivityScannerBinding.inflate(getLayoutInflater());
    }
}