package com.nathaniel.sample.surface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.OnItemClickListener;
import com.nathaniel.baseui.utility.ItemDecoration;
import com.nathaniel.sample.BuildConfig;
import com.nathaniel.sample.R;
import com.nathaniel.sample.adapter.AntivirusAdapter;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.utility.AbstractTask;
import com.nathaniel.utility.BitmapCacheUtils;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.JsonFileUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.PackageUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.ThreadManager;
import com.nathaniel.utility.entity.AntivirusEntity;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.entity.SpecimenEntity;

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
public class AntivirusActivity extends AbstractActivity implements OnItemClickListener {
    @BindView(R.id.common_header_back_iv)
    ImageView commonHeaderBackIv;
    @BindView(R.id.common_header_title_tv)
    TextView commonHeaderTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private AntivirusAdapter antivirusAdapter;
    private List<PackageEntity> packageEntityList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_antivirus;
    }

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
                List<PackageEntity> packageEntities = getAppList();
                if (!EmptyUtils.isEmpty(JsonFileUtils.getAntivirusList(getActivity()))) {
                    for (PackageEntity packageEntity : packageEntities) {
                        for (AntivirusEntity antivirusEntity : JsonFileUtils.getAntivirusList(getActivity())) {
                            if (packageEntity.getPackageName().equalsIgnoreCase(antivirusEntity.getPackageName())) {
                                packageEntity.setVestBagged(packageEntity.getSignature().equalsIgnoreCase(antivirusEntity.getSignature()));
                                break;
                            }
                        }
                        traverseSpecimen(JsonFileUtils.getSpecimensList(getActivity()), packageEntity, packageEntity.getPackageName());
                        if (!EmptyUtils.isEmpty(packageEntity.getProcessList())) {
                            for (String process : packageEntity.getProcessList()) {
                                traverseSpecimen(JsonFileUtils.getSpecimensList(getActivity()), packageEntity, process);
                            }
                        }
                    }
                }
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
        commonHeaderBackIv.setVisibility(View.VISIBLE);
        commonHeaderTitleTv.setText("手机安全检测");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        int itemSpace = (int) getResources().getDimension(R.dimen.common_height_divider);
        recyclerView.addItemDecoration(new ItemDecoration(itemSpace, ItemDecoration.LINEAR_LAYOUT_MANAGER));
        recyclerView.setAdapter(antivirusAdapter);
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

    private List<PackageEntity> getAppList() {
        LoggerUtils.logger(LoggerUtils.TAG, "AntivirusActivity-getAppList-160", "开始扫描本地APP");
        List<PackageEntity> packageEntities = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();
        int flags = PackageManager.GET_META_DATA
            | PackageManager.GET_SIGNATURES
            | PackageManager.GET_PERMISSIONS
            | PackageManager.GET_PROVIDERS
            | PackageManager.GET_RECEIVERS
            | PackageManager.GET_SERVICES
            | PackageManager.GET_ACTIVITIES;
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(flags);
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1
                || (applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1
                || applicationInfo.packageName.equals(BuildConfig.APPLICATION_ID)) {
                // TODO 忽略系统app和自己
                continue;
            }
            PackageEntity packageEntity = new PackageEntity();
            packageEntity.setAppName(applicationInfo.loadLabel(packageManager).toString());
            packageEntity.setPackageName(applicationInfo.packageName);
            packageEntity.setVersionName(packageInfo.versionName);
            packageEntity.setVersionCode(packageInfo.getLongVersionCode());
            packageEntity.setAppIcon(BitmapCacheUtils.getBitmapFromCache(getActivity(), applicationInfo, packageManager));

            Signature[] signatures = packageInfo.signatures;
            if (!EmptyUtils.isEmpty(signatures)) {
                packageEntity.setSignature(PackageUtils.getSignatureMd5(signatures[0]));
            }

            PermissionInfo[] permissionInfos = packageInfo.permissions;
            if (!EmptyUtils.isEmpty(permissionInfos)) {
                List<String> permissions = new ArrayList<>();
                for (PermissionInfo permissionInfo : permissionInfos) {
                    if (permissions.contains(permissionInfo.name)) {
                        continue;
                    }
                    permissions.add(permissionInfo.name);
                }
                packageEntity.setPermissionList(permissions);
            }

            ProviderInfo[] providerInfos = packageInfo.providers;
            if (!EmptyUtils.isEmpty(providerInfos)) {
                List<String> providers = new ArrayList<>();
                for (ProviderInfo providerInfo : providerInfos) {
                    if (providers.contains(providerInfo.name)) {
                        continue;
                    }
                    providers.add(providerInfo.name);
                }
                packageEntity.setProviderList(providers);
            }

            List<String> processes = new ArrayList<>();
            ActivityInfo[] activityInfos = packageInfo.activities;
            if (!EmptyUtils.isEmpty(activityInfos)) {
                List<String> activities = new ArrayList<>();
                for (ActivityInfo activityInfo : activityInfos) {
                    if (activities.contains(activityInfo.name)) {
                        continue;
                    }
                    activities.add(activityInfo.name);
                    if (!EmptyUtils.isEmpty(activityInfo.processName) && !processes.contains(activityInfo.processName)) {
                        processes.add(activityInfo.processName);
                    }
                }
                packageEntity.setActivityList(activities);
            }

            ActivityInfo[] receiverInfos = packageInfo.receivers;
            if (!EmptyUtils.isEmpty(receiverInfos)) {
                List<String> receivers = new ArrayList<>();
                for (ActivityInfo activityInfo : receiverInfos) {
                    receivers.add(activityInfo.name);
                    if (!EmptyUtils.isEmpty(activityInfo.processName) && !processes.contains(activityInfo.processName)) {
                        processes.add(activityInfo.processName);
                    }
                }
                packageEntity.setReceiverList(receivers);
            }
            ServiceInfo[] serviceInfos = packageInfo.services;
            if (!EmptyUtils.isEmpty(serviceInfos)) {
                List<String> services = new ArrayList<>();
                for (ServiceInfo serviceInfo : serviceInfos) {
                    services.add(serviceInfo.name);
                    if (!EmptyUtils.isEmpty(serviceInfo.processName) && !processes.contains(serviceInfo.processName)) {
                        processes.add(serviceInfo.processName);
                    }
                }
                packageEntity.setServiceList(services);
            }
            packageEntity.setProcessList(processes);
            packageEntities.add(packageEntity);
        }
        return packageEntities;
    }
}