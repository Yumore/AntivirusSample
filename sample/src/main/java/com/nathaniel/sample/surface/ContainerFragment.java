package com.nathaniel.sample.surface;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.nathaniel.baseui.AbstractFragment;
import com.nathaniel.sample.R;
import com.nathaniel.utility.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/6/7 - 20:50
 */
public class ContainerFragment extends AbstractFragment {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.refresh)
    Button refresh;
    private int classify;
    private String website;

    public static ContainerFragment getInstance(int classify, String website) {
        ContainerFragment containerFragment = new ContainerFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("classify", classify);
        arguments.putString("website", website);
        containerFragment.setArguments(arguments);
        return containerFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public void loadData() {
        Bundle arguments = getArguments();
        if (!EmptyUtils.isEmpty(arguments)) {
            classify = arguments.getInt("classify");
            website = arguments.getString("website");
        }
    }

    @Override
    public void bindView() {
        webView.loadUrl(website);
    }

    @OnClick({
        R.id.refresh
    })
    public void onClick(View view) {
        if (view.getId() == R.id.refresh) {
            webView.reload();
        }
    }
}
