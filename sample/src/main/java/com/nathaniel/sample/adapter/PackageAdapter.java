package com.nathaniel.sample.adapter;

import android.annotation.SuppressLint;
import android.os.Build;

import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.BaseViewHolder;
import com.nathaniel.sample.R;
import com.nathaniel.sample.utility.DataUtils;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.entity.PackageEntity;

import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/29/21 - 8:07 PM
 */
public class PackageAdapter extends BaseRecyclerAdapter<PackageEntity> {
    private static final String TAG = PackageAdapter.class.getSimpleName();

    /**
     * init data and layout
     *
     * @param layoutId layoutId
     * @param dataList dataList
     */
    public PackageAdapter(int layoutId, List<PackageEntity> dataList) {
        super(layoutId, dataList);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void bindDataToView(BaseViewHolder viewHolder, PackageEntity data) {
        if (EmptyUtils.isEmpty(data)) {
            return;
        }
        LoggerUtils.logger(TAG, data.toString());
        viewHolder.setImageDrawable(R.id.item_package_image, data.getAppIcon());
        viewHolder.setText(R.id.item_package_name, String.format("%s - 弹窗(%s)", data.getAppName(), data.isOverlay() ? "开" : "关"));
        viewHolder.setText(R.id.item_package_info, String.format("构建版本: %d, 版本: %s", data.getVersionCode(), data.getVersionName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            viewHolder.setText(R.id.item_package_data, String.format("WiFI流量: %s, 移动流量: %s, 流量总和: %s",
                DataUtils.getRealDataSize(data.getWifiTotal()),
                DataUtils.getRealDataSize(data.getMobileTotal()),
                DataUtils.getRealDataSize(data.getWifiTotal() + data.getMobileTotal())
            ));
        } else {
            viewHolder.setText(R.id.item_package_data, String.format("流量总和: %s",
                DataUtils.getRealDataSize(data.getWifiTotal())
            ));
        }
    }
}
