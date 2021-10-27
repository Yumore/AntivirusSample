package com.nathaniel.sample.surface;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.DetailAdapter;
import com.nathaniel.sample.databinding.ActivityApplicationBinding;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.sample.module.DetailEntity;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PackageEntity;

import java.util.List;


/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/10/17 - 8:25
 */
public class ApplicationActivity extends AbstractActivity<ActivityApplicationBinding> implements View.OnClickListener {
    private PackageEntity packageEntity;
    private DetailAdapter detailAdapter;

    @Override
    public void loadData() {
        packageEntity = SingletonUtils.getSingleton(AntivirusModule.class).getPackageEntity();
        List<DetailEntity> detailEntityList = SingletonUtils.getSingleton(AntivirusModule.class).getAppDetail();
        detailAdapter = new DetailAdapter(R.layout.item_detail_recycler_list, detailEntityList);
    }

    @Override
    public void bindView() {
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setVisibility(View.VISIBLE);
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setOnClickListener(this);
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setText("应用详情");
        viewBinding.antivirusDetailLayout.itemAntivirusLogo.setImageDrawable(packageEntity.getAppIcon());
        viewBinding.antivirusDetailLayout.itemAntivirusName.setText(String.format("%s\t\t%s", packageEntity.getAppName(), packageEntity.getPackageName()));
        viewBinding.antivirusDetailLayout.itemAntivirusVersion.setText(String.format("%s\t\t（%s）", packageEntity.getVersionName(), packageEntity.getVersionCode()));
        viewBinding.antivirusDetailLayout.itemAntivirusLevel.setText(String.format("危险等级\t\t（%s）", packageEntity.getVirusLevel()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.recyclerView.setLayoutManager(linearLayoutManager);
        viewBinding.recyclerView.setAdapter(detailAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.common_header_back_iv) {
            finish();
        }
    }

    @Override
    protected ActivityApplicationBinding initViewBinding() {
        return ActivityApplicationBinding.inflate(getLayoutInflater());
    }
}
