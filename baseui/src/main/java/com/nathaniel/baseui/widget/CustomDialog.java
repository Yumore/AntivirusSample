package com.nathaniel.baseui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.nathaniel.baseui.R;
import com.nathaniel.baseui.adapter.BaseRecyclerAdapter;
import com.nathaniel.baseui.adapter.OnItemClickListener;
import com.nathaniel.baseui.adapter.SimpleAdapter;
import com.nathaniel.baseui.utility.ItemDecoration;
import com.nathaniel.baseui.utility.ScreenUtils;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;

import java.util.Arrays;

/**
 * 也可以是用onCreateView实现
 *
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.valuelink.common.basic
 * @datetime 2020/5/26 - 15:18
 */
public class CustomDialog extends DialogFragment implements View.OnClickListener, OnItemClickListener {
    private static final String TAG = CustomDialog.class.getSimpleName();
    private final FragmentManager fragmentManager;
    private boolean outsideCancelable = true;
    private int gravity, titleGravity;
    private int dialogWidth;
    private int dialogHeight;
    private float dimAmount;
    private View.OnClickListener onPositiveClick, onNegativeClick, onConfirmClick;
    private CharSequence positiveText, negativeText, title, message;
    private OnItemClickListener onItemClickListener;
    private BaseRecyclerAdapter<?> baseRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    @DrawableRes
    private int titleDrawable;
    private Context context;
    private View customView;
    @LayoutRes
    private int layoutResource;
    private int[] ids;
    private View.OnClickListener onClickListener;
    private OnDialogCallback onDialogCallback;
    private boolean cleanable;
    private String[] items;
    private View rootView;
    private Integer[] widthAndHeight;
    private boolean confirmEnable;
    private boolean fullWidth;
    @DrawableRes
    private int backgroundResource;
    private SpannableString spannableString;
    private int windowStyle;

    private CustomDialog(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static CustomDialog getInstance(FragmentManager fragmentManager) {
        return new CustomDialog(fragmentManager);
    }

    public CustomDialog setOutsideCancelable(boolean outsideCancelable) {
        this.outsideCancelable = outsideCancelable;
        return this;
    }

    public CustomDialog setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public CustomDialog setTitleGravity(int titleGravity) {
        this.titleGravity = titleGravity;
        return this;
    }

    public CustomDialog setTitleDrawable(@DrawableRes int titleDrawable) {
        this.titleDrawable = titleDrawable;
        return this;
    }

    public CustomDialog setMessage(CharSequence message) {
        this.message = message;
        return this;
    }

    public CustomDialog setItems(String[] items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public CustomDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public CustomDialog setDialogWidth(int dialogWidth) {
        this.dialogWidth = dialogWidth;
        return this;
    }

    public CustomDialog setDialogHeight(int dialogHeight) {
        this.dialogHeight = dialogHeight;
        return this;
    }

    public CustomDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public CustomDialog setPositiveButton(CharSequence positiveText, View.OnClickListener onClickListener) {
        this.positiveText = positiveText;
        this.onPositiveClick = onClickListener;
        return this;
    }

    public CustomDialog setNegativeButton(CharSequence negativeText, View.OnClickListener onClickListener) {
        this.negativeText = negativeText;
        this.onNegativeClick = onClickListener;
        return this;
    }

    public CustomDialog setAdapter(BaseRecyclerAdapter<?> baseRecyclerAdapter) {
        this.baseRecyclerAdapter = baseRecyclerAdapter;
        return this;
    }

    public CustomDialog setLayoutManager(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public CustomDialog setAdapter(BaseRecyclerAdapter<?> baseRecyclerAdapter, OnItemClickListener onItemClickListener) {
        this.baseRecyclerAdapter = baseRecyclerAdapter;
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public CustomDialog setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public CustomDialog setCustomView(View customView, boolean cleanable) {
        this.customView = customView;
        this.cleanable = cleanable;
        return this;
    }

    public CustomDialog setLayoutResource(@LayoutRes int layoutResource, boolean cleanable) {
        this.layoutResource = layoutResource;
        this.cleanable = cleanable;
        return this;
    }

    public CustomDialog addClickListener(int[] ids, View.OnClickListener onClickListener) {
        this.ids = ids;
        this.onClickListener = onClickListener;
        return this;
    }

    public CustomDialog addClickListener(int[] ids, OnDialogCallback onDialogCallback) {
        this.ids = ids;
        this.onDialogCallback = onDialogCallback;
        return this;
    }

    public CustomDialog addClickListener(View.OnClickListener onClickListener, int... ids) {
        this.ids = ids;
        this.onClickListener = onClickListener;
        return this;
    }

    public CustomDialog addClickListener(OnDialogCallback onDialogCallback, int... ids) {
        this.ids = ids;
        this.onDialogCallback = onDialogCallback;
        return this;
    }

    public CustomDialog setConfirmEnable(boolean confirmEnable, View.OnClickListener onConfirmClick) {
        this.confirmEnable = confirmEnable;
        this.onConfirmClick = onConfirmClick;
        return this;
    }

    public CustomDialog setFullWidth(boolean fullWidth) {
        this.fullWidth = fullWidth;
        return this;
    }

    public CustomDialog setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
        return this;
    }

    public CustomDialog setSpannableString(SpannableString spannableString) {
        this.spannableString = spannableString;
        return this;
    }

    public CustomDialog getCustomDialog() {
        return this;
    }

    public CustomDialog setWindowStyle(int windowStyle) {
        this.windowStyle = windowStyle;
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    /**
     * 虽然 onCreateDialog 也可以
     * 但是优先级比较低所以使用该方法
     * View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_layout, null);
     * view.setFitsSystemWindows(true);
     * return setDialog(view);
     *
     * @see #onCreateDialog
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoggerUtils.logger(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (EmptyUtils.isEmpty(rootView)) {
            rootView = LayoutInflater.from(context).inflate(R.layout.common_dialog_layout, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this)
            .navigationBarWithEMUI3Enable(true)
            .statusBarColor(getStatusBarColor())
            .navigationBarWithKitkatEnable(true)
            .statusBarDarkFont(getStatusDarkMode())
            .navigationBarColor(getNavigateColor())
            .navigationBarDarkIcon(getNavigateDarkMode())
            .flymeOSStatusBarFontColor(getStatusBarColor())
            .autoStatusBarDarkModeEnable(getStatusDarkMode())
            .autoNavigationBarDarkModeEnable(getNavigateDarkMode())
            .init();
        initView(rootView);
    }

    private void initView(View rootView) {
        TextView titleText = rootView.findViewById(R.id.common_dialog_title_text);
        ImageView titleImage = rootView.findViewById(R.id.common_dialog_title_image);
        LinearLayout titleLayout = rootView.findViewById(R.id.common_dialog_title_layout);
        TextView messageText = rootView.findViewById(R.id.common_dialog_message_text);
        RecyclerView recyclerView = rootView.findViewById(R.id.common_dialog_list_layout);
        TextView dialogPositive = rootView.findViewById(R.id.common_dialog_positive_text);
        TextView dialogNegative = rootView.findViewById(R.id.common_dialog_negative_text);
        LinearLayout optionLayout = rootView.findViewById(R.id.common_dialog_option_layout);
        LinearLayout containerLayout = rootView.findViewById(R.id.common_dialog_container_layout);
        LinearLayout rootLayout = rootView.findViewById(R.id.common_dialog_root_layout);
        RelativeLayout confirmLayout = rootLayout.findViewById(R.id.common_dialog_confirm_rl);
        TextView confirmText = rootLayout.findViewById(R.id.common_dialog_confirm_tv);
        TextView cancelText = rootLayout.findViewById(R.id.common_dialog_cancel_tv);
        int itemSpace = (int) context.getResources().getDimension(R.dimen.common_padding_small_xxx);
        if (!EmptyUtils.isEmpty(title)) {
            if (titleGravity != 0) {
                titleText.setGravity(titleGravity);
            } else {
                titleText.setGravity(Gravity.CENTER);
            }
            titleText.setText(title);
            titleText.setVisibility(View.VISIBLE);
        }
        if (titleDrawable != 0) {
            titleImage.setImageResource(titleDrawable);
            titleImage.setVisibility(View.VISIBLE);
        }
        titleImage.setOnClickListener(this);
        if (EmptyUtils.isEmpty(title) && titleDrawable == 0) {
            titleLayout.setVisibility(View.GONE);
        } else {
            titleLayout.setVisibility(View.VISIBLE);
        }
        if (!EmptyUtils.isEmpty(spannableString)) {
            messageText.setText(spannableString);
            messageText.setVisibility(View.VISIBLE);
            messageText.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (!EmptyUtils.isEmpty(message)) {
            messageText.setText(message);
            messageText.setVisibility(View.VISIBLE);
        }
        if (!EmptyUtils.isEmpty(baseRecyclerAdapter)) {
            if (EmptyUtils.isEmpty(layoutManager)) {
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            }
            ItemDecoration itemDecoration = new ItemDecoration(itemSpace, ItemDecoration.LINEAR_LAYOUT_MANAGER);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setAdapter(baseRecyclerAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            baseRecyclerAdapter.setOnItemClickListener(this);
        }
        if (!EmptyUtils.isEmpty(items)) {
            baseRecyclerAdapter = new SimpleAdapter(R.layout.item_simple_recycler_list, Arrays.asList(items));
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            ItemDecoration itemDecoration = new ItemDecoration(itemSpace, ItemDecoration.LINEAR_LAYOUT_MANAGER);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setAdapter(baseRecyclerAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            baseRecyclerAdapter.setOnItemClickListener(this);
        }
        if (!EmptyUtils.isEmpty(positiveText)) {
            dialogPositive.setText(positiveText);
            dialogPositive.setVisibility(View.VISIBLE);
        }
        dialogPositive.setOnClickListener(this);
        if (!EmptyUtils.isEmpty(negativeText)) {
            dialogNegative.setText(negativeText);
            dialogNegative.setVisibility(View.VISIBLE);
        }
        dialogNegative.setOnClickListener(this);
        optionLayout.setVisibility(EmptyUtils.isEmpty(positiveText) && EmptyUtils.isEmpty(negativeText) ? View.GONE : View.VISIBLE);
        if (layoutResource != 0) {
            customView = LayoutInflater.from(context).inflate(layoutResource, null);
        }
        if (!EmptyUtils.isEmpty(customView)) {
            if (cleanable) {
                rootLayout.removeAllViews();
                rootLayout.addView(customView);
            } else {
                containerLayout.removeAllViews();
                containerLayout.addView(customView);
            }
            if (!EmptyUtils.isEmpty(ids)) {
                for (int id : ids) {
                    customView.findViewById(id).setOnClickListener(view -> {
                        if (!EmptyUtils.isEmpty(onClickListener)) {
                            onClickListener.onClick(view);
                        }
                        if (!EmptyUtils.isEmpty(onDialogCallback)) {
                            onDialogCallback.onDialog(view, getCustomDialog());
                        }
                    });
                }
            }
        }
        confirmText.setOnClickListener(this);
        cancelText.setOnClickListener(this);
        confirmLayout.setVisibility(confirmEnable ? View.VISIBLE : View.GONE);
        rootLayout.setBackgroundResource(backgroundResource != 0 ? backgroundResource : R.drawable.common_shape_round_pager_solid);
        setCancelable(outsideCancelable);
        setOutsideCancelable(outsideCancelable);
    }

    @Override
    public void onStart() {
        LoggerUtils.logger(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onStart();
        if (!EmptyUtils.isEmpty(getDialog())) {
            Window window = getDialog().getWindow();
            if (!EmptyUtils.isEmpty(window)) {
                widthAndHeight = ScreenUtils.getWidthAndHeight(window);
                window.setBackgroundDrawableResource(R.drawable.common_shape_transparent);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                window.setType(windowStyle);
                if (fullWidth) {
                    layoutParams.width = ScreenUtils.getScreenWidth(context);
                } else {
                    if (dialogWidth > 0) {
                        layoutParams.width = dialogWidth;
                    } else {
                        layoutParams.width = (int) (ScreenUtils.getScreenWidth(context) * 0.85);
                    }
                }
                if (dialogHeight > 0) {
                    layoutParams.height = dialogHeight;
                } else {
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                }
                if (dimAmount > 0) {
                    layoutParams.dimAmount = dimAmount;
                } else {
                    layoutParams.dimAmount = 0.55f;
                }
                if (gravity != 0) {
                    layoutParams.gravity = gravity;
                } else {
                    layoutParams.gravity = Gravity.CENTER;
                }
                if (gravity == Gravity.BOTTOM && ImmersionBar.hasNavigationBar(this)) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) rootView.getLayoutParams();
                    marginLayoutParams.bottomMargin = ImmersionBar.getNavigationBarHeight(this);
                    rootView.setLayoutParams(marginLayoutParams);
                }
                window.setAttributes(layoutParams);
            }
            if (EmptyUtils.isEmpty(getActivity())) {
                throw new NullPointerException("getActivity is null in " + TAG);
            }
            getDialog().setCancelable(outsideCancelable);
            getDialog().setCanceledOnTouchOutside(outsideCancelable);
            getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
            ImmersionBar.with(getActivity(), getDialog())
                .navigationBarWithEMUI3Enable(true)
                .statusBarColor(getStatusBarColor())
                .navigationBarWithKitkatEnable(true)
                .statusBarDarkFont(getStatusDarkMode())
                .navigationBarColor(getNavigateColor())
                .navigationBarDarkIcon(getNavigateDarkMode())
                .flymeOSStatusBarFontColor(getStatusBarColor())
                .init();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!EmptyUtils.isEmpty(getDialog()) && !EmptyUtils.isEmpty(getDialog().getWindow())) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.common_dialog_title_image) {
            dismiss();
        } else if (view.getId() == R.id.common_dialog_positive_text) {
            if (!EmptyUtils.isEmpty(onPositiveClick)) {
                onPositiveClick.onClick(view);
            }
            dismiss();
        } else if (view.getId() == R.id.common_dialog_negative_text) {
            if (!EmptyUtils.isEmpty(onNegativeClick)) {
                onNegativeClick.onClick(view);
            }
            dismiss();
        } else if (view.getId() == R.id.common_dialog_confirm_tv) {
            if (!EmptyUtils.isEmpty(onConfirmClick)) {
                onConfirmClick.onClick(view);
            }
            dismiss();
        } else if (view.getId() == R.id.common_dialog_cancel_tv) {
            dismiss();
        }
    }


    protected int getNavigateColor() {
        return R.color.common_color_transparent;
    }

    protected boolean getNavigateDarkMode() {
        return true;
    }

    protected boolean getStatusDarkMode() {
        return true;
    }

    protected int getStatusBarColor() {
        return R.color.common_color_white;
    }

    public void showDialog() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(CustomDialog.class.getSimpleName());
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        // TODO 显示一个Fragment并且给该Fragment添加一个Tag，可通过findFragmentByTag()找到该Fragment
        // TODO java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        // show(fragmentTransaction, CustomDialog.class.getSimpleName());
        fragmentTransaction.add(getCustomDialog(), CustomDialog.class.getSimpleName());
        //fragmentManager.executePendingTransactions();
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        ImmersionBar.destroy(getCustomDialog());
    }

    @Override
    public void onItemClick(@NonNull BaseRecyclerAdapter<?> adapter, @NonNull View view, int position) {
        if (!EmptyUtils.isEmpty(onItemClickListener)) {
            onItemClickListener.onItemClick(adapter, view, position);
        }
        if (!confirmEnable) {
            dismiss();
        }
    }
}
