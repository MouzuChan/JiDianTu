package com.zhbit.application;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CurveLineView mCurveLineView;
    private ArrayList<Integer> mPointData;
    private int mMaxY = 0;
    private int mMinY = 0;
    private int mPointSpace;
    private int mMaxData = 0;

    private final static String FILE_NAME = "jidian.txt";
    private final static String TAG = "MainActivity";

    private float dataToPixel;
    private AxesView mAxesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCurveLineView = (CurveLineView) findViewById(R.id.curve_line_view);
        mAxesView = (AxesView) findViewById(R.id.axes_view);
        mPointData = new ArrayList<Integer>();
        initPointDataFromFile();
        initCurveLineView();
        initAxesView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initPointDataFromFile(){
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + FILE_NAME);
        BufferedReader reader = null;
        try {
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!TextUtils.isEmpty(line)) {
                        mPointData.add(Integer.valueOf(line));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initCurveLineView() {
        Resources resources = getResources();
        mPointSpace = mCurveLineView.getPointSpace();
        int height = resources.getDimensionPixelOffset(R.dimen.curve_line_view_height);
        int width = mPointSpace * (mPointData.size() + 1);
        mCurveLineView.setHeight(height);
        mCurveLineView.setWidth(width);
        int offset = resources.getDimensionPixelOffset(R.dimen.axes_view_offset);
        mMaxY = height - offset;
        mMinY = offset;
        for (Integer integer : mPointData) {
            if (integer > mMaxData) {
                mMaxData = integer;
            }
        }
        mMaxData = (mMaxData / 100 + 1) * 100;
        //Added origin point.
        Point p = new Point();
        p.setY(mMaxY);
        p.setX(0);
        mCurveLineView.addPoint(p);

        dataToPixel = ((float)(mMaxY - mMinY) / mMaxData);
        for (int i = 0; i < mPointData.size(); i++) {
            Point point = new Point();
            point.setX(i * mPointSpace + mPointSpace);
            point.setY(mMaxY - (mPointData.get(i) * dataToPixel));
            mCurveLineView.addPoint(point);
        }
        mCurveLineView.invalidate();
    }

    public void initAxesView() {
        mAxesView.setMaxData(mMaxData);
        mAxesView.setDataToPixel(dataToPixel);
        mAxesView.invalidate();
    }
}
