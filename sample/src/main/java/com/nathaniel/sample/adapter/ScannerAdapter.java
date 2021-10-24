package com.nathaniel.sample.adapter;

import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.BaseViewHolder;
import com.nathaniel.sample.R;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.entity.PathEntity;

import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.adapter
 * @datetime 2021/7/31 - 05:53
 */
public class ScannerAdapter extends BaseRecyclerAdapter<PathEntity> {
    /**
     * init data and layout
     *
     * @param layoutId layoutId
     * @param dataList dataList
     */
    public ScannerAdapter(int layoutId, List<PathEntity> dataList) {
        super(layoutId, dataList);
    }

    @Override
    public void bindDataToView(BaseViewHolder viewHolder, PathEntity data) {
        if (EmptyUtils.isEmpty(data)) {
            return;
        }
        viewHolder.setText(R.id.item_scanner_path_tv, data.getFolderPath());
        viewHolder.setText(R.id.item_scanner_time_tv, String.valueOf(EmptyUtils.isEmpty(data.getFolderFiles()) ? 0 : data.getFolderFiles().size()));
    }
}