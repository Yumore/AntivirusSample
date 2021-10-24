package com.nathaniel.baseui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.nathaniel.baseui.utility.FragmentCallback;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @package com.nathaniel.baseui.binding
 * @datetime 2021/3/31 - 19:57
 */
public abstract class AbstractFragment extends Fragment implements IViewBinding {
    private static final String TAG = AbstractFragment.class.getSimpleName();
    private AlertDialog alertDialog;
    private Context context;
    private View rootView;
    private Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        requireFragmentManager().registerFragmentLifecycleCallbacks(new FragmentCallback(), true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(context).inflate(getLayoutId(), container, false);
        loadData();
        initView();
        bindView();
        return rootView;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(getFragment(), rootView);
    }

    @Override
    public void beforeInit() {
        LoggerUtils.logger(TAG, "beforeUI()");
    }

    protected final Fragment getFragment() {
        return this;
    }


    @Override
    public void showLoading(CharSequence message) {
        dismissLoading();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_dialog_loading, null);
        TextView textView = view.findViewById(R.id.loading_dialog_message);
        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
        } else {
            textView.setVisibility(View.GONE);
        }
        alertDialog = new AlertDialog.Builder(context, R.style.CustomDialog)
            .setCancelable(false)
            .setView(view)
            .create();
        alertDialog.show();
        Window dialogWindow = alertDialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = .35f;
            dialogWindow.setAttributes(layoutParams);
        }
    }

    @Override
    public void dismissLoading() {
        if (!EmptyUtils.isEmpty(alertDialog) && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    public <T extends View> T obtainView(int viewId) {
        return rootView.findViewById(viewId);
    }

    protected <T extends View> T obtainView(View parent, int viewId) {
        return parent.findViewById(viewId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EmptyUtils.isEmpty(unbinder)) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
