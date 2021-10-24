package com.nathaniel.baseui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.nathaniel.baseui.R;


/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.fontsize.widget
 * @datetime 2021/5/18 - 14:12
 */
public class CustomTextView extends AppCompatTextView {
    private static final String TAG = CustomTextView.class.getSimpleName();

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
        Paint paint = new Paint();
        Log.e(TAG, "textSize:" + textSize);
        paint.setTextSize(textSize);
        final Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.CustomTextView_textSize, 12));
        int top = (int) Math.ceil(Math.abs((fontMetricsInt.top - fontMetricsInt.ascent) / 2.0));
        Log.e(TAG, "top" + top);
        setPadding(0, -(Math.abs(fontMetricsInt.top - fontMetricsInt.ascent) + top), 0, fontMetricsInt.top - fontMetricsInt.ascent);
        typedArray.recycle();
        post(() -> Log.e(TAG, "getHeight()" + getHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
