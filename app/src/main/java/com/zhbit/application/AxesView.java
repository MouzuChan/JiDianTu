package com.zhbit.application;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by L on 2016/9/3.
 */
public class AxesView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    private int mMaxData;
    private float dataToPixel;

    private int yAxisPointCount;

    public AxesView(Context context) {
        super(context);
        init(context, null);
    }

    public AxesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.curve_line_width));
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.axes_view_text_size));
        yAxisPointCount = getResources().getInteger(R.integer.y_axis_point_count);
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AxesView);
            yAxisPointCount = a.getInteger(R.styleable.AxesView_yAxisPointCount, yAxisPointCount);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Resources resources = getResources();
        int offset = resources.getDimensionPixelOffset(R.dimen.axes_view_offset);
        int arrowSize = resources.getDimensionPixelSize(R.dimen.axes_view_arrow_size);
        mWidth = getWidth();
        mHeight = getHeight() - offset;

        canvas.drawLine(offset, 0, offset, mHeight, mPaint);
        canvas.drawLine(offset, mHeight, mWidth, mHeight, mPaint);
        canvas.drawLine(offset - arrowSize, arrowSize, offset, 0, mPaint);
        canvas.drawLine(offset + arrowSize, arrowSize, offset, 0, mPaint);
        canvas.drawLine(mWidth - arrowSize, mHeight - arrowSize, mWidth, mHeight, mPaint);
        canvas.drawLine(mWidth - arrowSize, mHeight + arrowSize, mWidth, mHeight, mPaint);
        mPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.axes_view_text_size));
        canvas.drawText("肌电强度", offset + resources.getDimensionPixelOffset(R.dimen.activity_horizontal_margin),
                resources.getDimensionPixelOffset(R.dimen.activity_horizontal_margin), mPaint);

        if (mMaxData > 0 && yAxisPointCount > 0) {
            int data = mMaxData / yAxisPointCount;
            for (int i = 1; i <= yAxisPointCount; i++) {
                canvas.drawLine(offset + arrowSize, mHeight - (i * data * dataToPixel), offset, mHeight - (i * data * dataToPixel),
                        mPaint);
                canvas.drawText("" + (i * data), 0,  mHeight - (i * data * dataToPixel) + arrowSize, mPaint);
            }
        }
    }

    public void setMaxData(int mMaxData) {
        this.mMaxData = mMaxData;
    }

    public void setDataToPixel(float dataToPixel) {
        this.dataToPixel = dataToPixel;
    }
}
