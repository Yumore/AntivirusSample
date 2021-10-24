package com.nathaniel.sample.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.BaseViewHolder;
import com.nathaniel.baseui.utility.ItemDecoration;
import com.nathaniel.sample.R;
import com.nathaniel.sample.module.DetailEntity;
import com.nathaniel.utility.EmptyUtils;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.adapter
 * @datetime 2021/10/17 - 10:27
 */
public class DetailAdapter extends BaseRecyclerAdapter<DetailEntity> {
    /**
     * init data and layout
     *
     * @param layoutId layoutId
     * @param dataList dataList
     */
    public DetailAdapter(int layoutId, List<DetailEntity> dataList) {
        super(layoutId, dataList);
    }

    @Override
    public void bindDataToView(BaseViewHolder viewHolder, DetailEntity data) {
        if (EmptyUtils.isEmpty(data)) {
            return;
        }
        viewHolder.setText(R.id.item_detail_name, data.getItemName());
        RecyclerView recyclerView = viewHolder.getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ContentAdapter contentAdapter = new ContentAdapter(R.layout.item_text_recycler_list, data.getItemValue());
        recyclerView.setLayoutManager(linearLayoutManager);
        int itemSpace = (int) getContext().getResources().getDimension(R.dimen.common_height_divider);
        recyclerView.addItemDecoration(new ItemDecoration(itemSpace, ItemDecoration.LINEAR_LAYOUT_MANAGER));
        recyclerView.setAdapter(contentAdapter);
    }
}
