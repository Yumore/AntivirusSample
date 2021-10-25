package com.nathaniel.sample.surface;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.OnItemClickListener;
import com.nathaniel.baseui.utility.ItemDecoration;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.AntivirusAdapter;
import com.nathaniel.sample.databinding.ActivityAntivirusBinding;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.utility.AbstractTask;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.ThreadManager;
import com.nathaniel.utility.entity.AntivirusEntity;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.entity.SpecimenEntity;
import com.nathaniel.utility.helper.PackageDaoHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/10/16 - 13:03
 */
public class AntivirusActivity extends AbstractActivity<ActivityAntivirusBinding> implements OnItemClickListener {
    private AntivirusAdapter antivirusAdapter;
    private List<PackageEntity> packageEntityList;

    @Override
    public void loadData() {
        packageEntityList = new ArrayList<>();
        antivirusAdapter = new AntivirusAdapter(R.layout.item_antivirus_recycler_list, packageEntityList);
        ThreadManager.getInstance().executor(new AbstractTask<List<PackageEntity>>() {
            @Override
            public void prepareRunnable() {
                super.prepareRunnable();
                showLoading("因扫描所有APP信息比较耗费时间，且需要分开统计，所以时间会比较长，请耐心等待......");
            }

            @Override
            public void runnableCallback(List<PackageEntity> packageEntities) {
                dismissLoading();
                if (packageEntityList.size() > 0) {
                    packageEntityList.clear();
                }
                packageEntityList.addAll(packageEntities);
                antivirusAdapter.notifyDataSetChanged();
            }

            @Override
            protected List<PackageEntity> doRunnableCode() {
                List<PackageEntity> packageEntities = SingletonUtils.getSingleton(AntivirusModule.class).getPackageEntities();
                List<AntivirusEntity> antivirusEntities = SingletonUtils.getSingleton(AntivirusModule.class).getAntivirusEntities();
                List<SpecimenEntity> specimenEntities = SingletonUtils.getSingleton(AntivirusModule.class).getSpecimenEntities();
                if (!EmptyUtils.isEmpty(antivirusEntities)) {
                    for (PackageEntity packageEntity : packageEntities) {
                        for (AntivirusEntity antivirusEntity : antivirusEntities) {
                            if (packageEntity.getPackageName().equalsIgnoreCase(antivirusEntity.getPackageName())) {
                                packageEntity.setVestBagged(packageEntity.getSignature().equalsIgnoreCase(antivirusEntity.getSignature()));
                                break;
                            }
                        }
                        traverseSpecimen(specimenEntities, packageEntity, packageEntity.getPackageName());
                        if (!EmptyUtils.isEmpty(packageEntity.getProcessList())) {
                            for (String process : packageEntity.getProcessList()) {
                                traverseSpecimen(specimenEntities, packageEntity, process);
                            }
                        }
                    }
                }
                SingletonUtils.getInstance(PackageDaoHelper.class).inertOrUpdate(packageEntities);
                return packageEntities;
            }
        });
    }

    private void traverseSpecimen(List<SpecimenEntity> specimenEntities, PackageEntity packageEntity, String processName) {
        for (SpecimenEntity specimenEntity : specimenEntities) {
            if (processName.equalsIgnoreCase(specimenEntity.getVirusName())) {
                packageEntity.setVirusName(specimenEntity.getVirusName());
                packageEntity.setVirusNumber(specimenEntity.getVirusNumber());
                packageEntity.setVirusLevel(specimenEntity.getVirusLevel());
                packageEntity.setVirusDescribe(specimenEntity.getVirusDescribe());
                break;
            }
        }
    }

    @Override
    public void bindView() {
        viewBinding.commonHeaderRootLayout.commonHeaderBackIv.setVisibility(View.VISIBLE);
        viewBinding.commonHeaderRootLayout.commonHeaderTitleTv.setText("手机安全检测");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewBinding.recyclerView.setLayoutManager(linearLayoutManager);
        int itemSpace = (int) getResources().getDimension(R.dimen.common_height_divider);
        viewBinding.recyclerView.addItemDecoration(new ItemDecoration(itemSpace, ItemDecoration.LINEAR_LAYOUT_MANAGER));
        viewBinding.recyclerView.setAdapter(antivirusAdapter);
        antivirusAdapter.setOnItemClickListener(this);
    }

    @OnClick({
        R.id.common_header_back_iv
    })
    public void onClick(View view) {
        if (view.getId() == R.id.common_header_back_iv) {
            finish();
        }
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter<?> adapter, View view, int position) {
        PackageEntity packageEntity = packageEntityList.get(position);
        SingletonUtils.getSingleton(AntivirusModule.class).setPackageEntity(packageEntity);
        startActivity(new Intent(getActivity(), ApplicationActivity.class));
    }

    @Override
    protected ActivityAntivirusBinding initViewBinding() {
        return ActivityAntivirusBinding.inflate(getLayoutInflater());
    }
}