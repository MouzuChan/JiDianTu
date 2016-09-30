package com.zhbit.application;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by L on 2016/9/4.
 */
public class Point {
    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void fuck(Context context) {
        Toast.makeText(context, "Fuck", Toast.LENGTH_SHORT);
    }
}
