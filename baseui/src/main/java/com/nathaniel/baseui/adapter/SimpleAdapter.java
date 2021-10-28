package com.nathaniel.baseui.adapter;

import androidx.annotation.Nullable;

import com.nathaniel.baseui.R;
import com.nathaniel.utility.EmptyUtils;

import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @datetime 2020/5/27 - 15:18
 */
public class SimpleAdapter extends BaseRecyclerAdapter<String> {

    public SimpleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    public void bindDataToView(BaseViewHolder viewHolder, String data) {
        if (EmptyUtils.isEmpty(data)) {
            return;
        }
        viewHolder.setText(R.id.item_simple_text_tv, data);
    }
}
