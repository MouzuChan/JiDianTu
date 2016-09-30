package com.zhbit.application;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by L on 2016/9/3.
 */
public class CurveLineView extends View {
    private int mPointSpace;

    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    private ArrayList<Point> mPointList;

    public CurveLineView(Context context) {
        super(context, null);
    }

    public CurveLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPointList = new ArrayList<Point>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.curve_line_width));
        mPointSpace = getResources().getDimensionPixelOffset(R.dimen.curve_point_default_space);
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CurveLineView);
            mPointSpace = a.getDimensionPixelOffset(R.styleable.CurveLineView_pointSpace, mPointSpace);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }


    private void drawPoint(Canvas canvas) {
        Point point;
        for (int i = 0; i < mPointList.size(); i++) {
            point = mPointList.get(i);
            canvas.drawPoint(point.getX(), point.getY(), mPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        Point starPoint;
        Point stopPoint;
        for (int i = 1; i < mPointList.size(); i++) {
            starPoint = mPointList.get(i - 1);
            stopPoint = mPointList.get(i);
            canvas.drawLine(starPoint.getX(), starPoint.getY(), stopPoint.getX(), stopPoint.getY(), mPaint);
        }
    }

    public void addPoint(Point point) {
        mPointList.add(point);
    }

    public int getPointSpace() {
        return mPointSpace;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }
}
