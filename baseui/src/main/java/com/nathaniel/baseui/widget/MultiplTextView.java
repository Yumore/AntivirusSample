package com.nathaniel.baseui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import com.nathaniel.baseui.R;


/**
 * Created by domain on 2018-08-06.
 * Email:sunlongyue@foxmail.com
 * describe:
 */

public class MultiplTextView extends AppCompatTextView {

    private final Paint paint;
    private final Rect rect = new Rect();
    private Paint.FontMetricsInt fontMetricsInt;
    private Boolean bRunText = false;
    private Boolean bRemoveDefaultPadding = false;
    private String fontPath = null;
    private Boolean bGradient = false;
    private int gradientStartColor;
    private int gradientCenterColor;
    private int gradietendColor;
    private Typeface typeface;
    /**
     * 自定义view的几个方法的执行顺序:
     * 构造方法->measure->onSizeChanged->onDraw
     */
    private int width = 0;
    private int height = 0;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;

    //java 代码块 ，先于构造函数执行
    {
        paint = getPaint(); //获取textview的画笔 获取画笔属性

    }

    public MultiplTextView(Context context) {
        super(context);
    }

    public MultiplTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    public MultiplTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        init();
        option(canvas);
        drawText(canvas);
//        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (bRemoveDefaultPadding) {
            calculateTextParams();
            setMeasuredDimension(rect.right - rect.left, -rect.top + rect.bottom); //设置view的宽高为text的宽高
        }
    }

    private void init() {
        mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
            new int[]{gradientStartColor, gradientCenterColor, gradietendColor},
            null, Shader.TileMode.MIRROR);
        mGradientMatrix = new Matrix();
        Log.e("sss", "gradientStartColor:" + gradientStartColor + "gradietendColor:" + gradietendColor);
    }

    //初始化属性
    private void initAttributes(Context context, AttributeSet attrs) {
        //从xml的属性中获取到字体颜色与string
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MultiplTextView);
        bRunText = ta.getBoolean(R.styleable.MultiplTextView_runText, false);
        bGradient = ta.getBoolean(R.styleable.MultiplTextView_gradient, false);
        bRemoveDefaultPadding = ta.getBoolean(R.styleable.MultiplTextView_removeDefaultPadding, false);
        gradientStartColor = ta.getColor(R.styleable.MultiplTextView_gradientStartColor, getCurrentTextColor());//默认为当前颜色
        gradientCenterColor = ta.getColor(R.styleable.MultiplTextView_gradientCenterColor, getCurrentTextColor());//默认为当前颜色
        gradietendColor = ta.getColor(R.styleable.MultiplTextView_gradientEndColor, Color.BLACK);
        fontPath = ta.getString(R.styleable.MultiplTextView_textFont);
        if (!TextUtils.isEmpty(fontPath)) {
            typeface = Typeface.createFromAsset(getResources().getAssets(), fontPath); //获取字体
            paint.setTypeface(typeface);
        }
        ta.recycle();
    }

    //选择效果 执行对应方法
    private void option(Canvas canvas) {
        if (bGradient) {
            paint.setShader(mLinearGradient);
        }
        if (bRunText) {
            runText();
        }
    }

    //文字颜色滚动
    public void runText() {
        if (mGradientMatrix != null) {
            mTranslate += width / 5;
            if (mTranslate > 2 * width) {
                mTranslate = -width;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }


    //获取text宽高
    private String calculateTextParams() {
        final String text = getText().toString();
        final int textLength = text.length();
//        mPaint.setTextSize(getTextSize());
        paint.getTextBounds(text, 0, textLength, rect);
        if (textLength == 0) {
            rect.right = rect.left;
        }
        return text;
    }

    //用drawText方法画text
    private void drawText(Canvas canvas) {
        final String text = calculateTextParams();
        final int left = rect.left;
        final int bottom = rect.bottom;
        rect.offset(-rect.left, -rect.top);
        paint.setAntiAlias(true);
        paint.setColor(getCurrentTextColor());
        canvas.drawText(text, -left, rect.bottom - bottom, paint);
    }

}

