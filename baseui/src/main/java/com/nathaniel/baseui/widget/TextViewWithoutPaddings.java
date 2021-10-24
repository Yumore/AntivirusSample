package com.nathaniel.baseui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 2021/5/18 - 11:11
 */
public class TextViewWithoutPaddings extends AppCompatTextView {
    private final Paint paint = new Paint();

    private final Rect rect = new Rect();

    public TextViewWithoutPaddings(Context context) {
        super(context);
    }

    public TextViewWithoutPaddings(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewWithoutPaddings(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        final String text = calculateTextParams();
        final int left = rect.left;
        final int bottom = rect.bottom;
        rect.offset(-rect.left, -rect.top);
        paint.setAntiAlias(true);
        paint.setColor(getCurrentTextColor());
        canvas.drawText(text, -left, rect.bottom - bottom, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculateTextParams();
        setMeasuredDimension(rect.right - rect.left, -rect.top + rect.bottom);
    }

    private String calculateTextParams() {
        final String text = getText().toString();
        final int textLength = text.length();
        paint.setTextSize(getTextSize());
        paint.getTextBounds(text, 0, textLength, rect);
        if (textLength == 0) {
            rect.right = rect.left;
        }
        return text;
    }
}
