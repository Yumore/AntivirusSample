package com.nathaniel.sample.adapter;

import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.BaseViewHolder;
import com.nathaniel.sample.R;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.SingletonUtils;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.adapter
 * @datetime 2021/10/17 - 10:31
 */
public class ContentAdapter extends BaseRecyclerAdapter<String> {
    /**
     * init data and layout
     *
     * @param layoutId layoutId
     * @param dataList dataList
     */
    public ContentAdapter(int layoutId, List<String> dataList) {
        super(layoutId, dataList);
    }

    @Override
    public void bindDataToView(BaseViewHolder viewHolder, String data) {
        if (EmptyUtils.isEmpty(data)) {
            return;
        }
        viewHolder.setText(R.id.item_detail_value, data);
        boolean dangerous = SingletonUtils.getSingleton(AntivirusModule.class).isDangerous(getContext(), data);
        viewHolder.setTextColor(R.id.item_detail_value, getContext().getColor(dangerous ? R.color.common_color_red_dark : R.color.common_color_black_dark));
    }
}
