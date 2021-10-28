package com.nathaniel.baseui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.nathaniel.baseui.R;


/**
 * 带删除功能的EditText
 *
 * @author Nathaniel
 */
public class ClearEditText extends AppCompatEditText implements OnFocusChangeListener, TextWatcher {
    private Drawable leftDrawable;
    private boolean hasFocus;
    @DrawableRes
    private int leftDrawableResId;
    private boolean clearEnable = false;
    @DrawableRes
    private int clearDrawableResId = R.mipmap.icon_delete_grey;

    private float clearDrawableSize = dp2px(20);
    private float leftDrawableSize = dp2px(20);

    public ClearEditText(Context context) {
        super(context);
        init(null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClearEditText);
            clearDrawableResId = typedArray.getResourceId(R.styleable.ClearEditText_clearDrawable, clearDrawableResId);
            clearDrawableSize = typedArray.getDimension(R.styleable.ClearEditText_clearSize, dp2px(20));
            leftDrawableResId = typedArray.getResourceId(R.styleable.ClearEditText_leftDrawable, -1);
            leftDrawableSize = typedArray.getDimension(R.styleable.ClearEditText_leftSize, dp2px(20));
            typedArray.recycle();
        }
        initClearIcon();
        initLeftIcon();
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setCompoundDrawablePadding(8);
    }

    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public void setClearDrawable(@DrawableRes int resId) {
        this.clearDrawableResId = resId;
        invalidate();
    }

    private void initClearIcon() {
        Drawable clearDrawable = getCompoundDrawables()[2];
        if (clearDrawable == null) {
            clearDrawable = ContextCompat.getDrawable(getContext(), clearDrawableResId);
        }
        clearDrawable.setBounds(0, 0, (int) clearDrawableSize, (int) clearDrawableSize);
        Drawable right = clearEnable ? clearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @SuppressLint("ResourceType")
    private void initLeftIcon() {
        if (leftDrawableResId > 0) {
            leftDrawable = getCompoundDrawables()[0];
            if (leftDrawable == null) {
                leftDrawable = ContextCompat.getDrawable(getContext(), leftDrawableResId);
            }
            leftDrawable.setBounds(0, 0, (int) leftDrawableSize, (int) leftDrawableSize);
        }
        Drawable left = leftDrawable;
        setCompoundDrawables(left, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    public void setLeftIconResource(@DrawableRes int resId) {
        leftDrawableResId = resId;
        initLeftIcon();
    }

    private void setClearIconVisible(boolean visible) {
        this.clearEnable = visible;
        initClearIcon();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置震动效果
     */
    public void setShakeAnimation() {
        Animation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(5));
        animation.setDuration(1000);
        this.setAnimation(animation);
    }
}
