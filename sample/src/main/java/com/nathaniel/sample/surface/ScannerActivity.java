package com.nathaniel.sample.surface;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.ScannerAdapter;
import com.nathaniel.sample.databinding.ActivityScannerBinding;
import com.nathaniel.sample.module.ScannerModel;
import com.nathaniel.utility.DiskUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PathEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/7/30 - 09:56
 */
public class ScannerActivity extends AbstractActivity<ActivityScannerBinding> implements View.OnClickListener {

    private String rootPath;
    private ScannerAdapter scannerAdapter;
    private List<PathEntity> pathEntityList;


    @Override
    public void loadData() {
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
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
        viewBinding.scannerStartingBtn.setOnClickListener(this);
        viewBinding.scannerStoppingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.common_header_back_iv) {
            finish();
        } else if (view.getId() == R.id.scanner_starting_btn) {
            SingletonUtils.getSingleton(DiskUtils.class).scannerFiles(rootPath, pathEntities -> {
                if (pathEntityList.size() > 0) {
                    pathEntityList.clear();
                }
                pathEntityList.addAll(pathEntities);
                scannerAdapter.notifyDataSetChanged();
            });
//            List<Map<String, Object>> mapList = SingletonUtils.getSingleton(DiskUtils.class).scannerOldFiles(rootPath);
//            for (Map<String, Object> objectMap : mapList) {
//                for (Map.Entry<String, Object> objectEntry : objectMap.entrySet()) {
//                    LoggerUtils.logger(TAG, objectEntry.getKey() + " => " + objectEntry.getValue());
//                }
//            }
        } else if (view.getId() == R.id.scanner_stopping_btn) {
            SingletonUtils.getSingleton(DiskUtils.class).stopScanner();
        }
    }

    @Override
    protected ActivityScannerBinding initViewBinding() {
        return ActivityScannerBinding.inflate(getLayoutInflater());
    }
}