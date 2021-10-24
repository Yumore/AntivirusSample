package com.nathaniel.sample.adapter;

import androidx.annotation.Nullable;

import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.BaseViewHolder;
import com.nathaniel.sample.R;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.entity.PackageEntity;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.awen.breakpoint.ui
 * @datetime 2021/6/12 - 20:37
 */
public class AntivirusAdapter extends BaseRecyclerAdapter<PackageEntity> {
    public AntivirusAdapter(int layoutResId, @Nullable List<PackageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    public void bindDataToView(BaseViewHolder viewHolder, PackageEntity data) {
        if (EmptyUtils.isEmpty(data)) {
            return;
        }
        viewHolder.setImageDrawable(R.id.item_antivirus_logo, data.getAppIcon());
        viewHolder.setText(R.id.item_antivirus_name, String.format("%s\t\t(%s)", data.getAppName(), data.getPackageName()));
        viewHolder.setText(R.id.item_antivirus_version, String.format("%s\t\t(%s)", data.getVersionName(), data.getVersionCode()));
        viewHolder.setText(R.id.item_antivirus_level, String.format("危险等级\t\t(%s)", data.getVirusLevel()));
    }
}
