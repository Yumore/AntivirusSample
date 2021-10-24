package com.nathaniel.sample.surface;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.DetailAdapter;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.sample.module.DetailEntity;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PackageEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/10/17 - 8:25
 */
public class ApplicationActivity extends AbstractActivity {
    @BindView(R.id.common_header_back_iv)
    ImageView commonHeaderBackIv;
    @BindView(R.id.common_header_title_tv)
    TextView commonHeaderTitleTv;
    @BindView(R.id.item_antivirus_logo)
    ImageView appIcon;
    @BindView(R.id.item_antivirus_name)
    TextView appName;
    @BindView(R.id.item_antivirus_version)
    TextView appVersion;
    @BindView(R.id.item_antivirus_level)
    TextView appLevel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PackageEntity packageEntity;
    private DetailAdapter detailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_application;
    }

    @Override
    public void loadData() {
        packageEntity = SingletonUtils.getSingleton(AntivirusModule.class).getPackageEntity();
        List<DetailEntity> detailEntityList = SingletonUtils.getSingleton(AntivirusModule.class).getAppDetail();
        detailAdapter = new DetailAdapter(R.layout.item_detail_recycler_list, detailEntityList);
    }

    @Override
    public void bindView() {
        commonHeaderBackIv.setVisibility(View.VISIBLE);
        commonHeaderTitleTv.setText("应用详情");
        appIcon.setImageDrawable(packageEntity.getAppIcon());
        appName.setText(String.format("%s\t\t%s", packageEntity.getAppName(), packageEntity.getPackageName()));
        appVersion.setText(String.format("%s\t\t（%s）", packageEntity.getVersionName(), packageEntity.getVersionCode()));
        appLevel.setText(String.format("危险等级\t\t（%s）", packageEntity.getVirusLevel()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(detailAdapter);
    }

    @OnClick(R.id.common_header_back_iv)
    public void onClick(View view) {
        if (view.getId() == R.id.common_header_back_iv) {
            finish();
        }
    }
}
