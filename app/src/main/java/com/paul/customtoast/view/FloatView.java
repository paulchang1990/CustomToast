/*
 * Copyright 2015 Paul Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paul.customtoast.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * 一个浮动于其他应用之上的View基类.
 * Created by Paul Chang on 2015/11/19.
 */
public abstract class FloatView {
    protected final WindowManager mWM;
    protected final WindowManager.LayoutParams mLayoutParams;
    protected View mContentView;

    public FloatView(Context context, View view) {
        mWM = (WindowManager) context.getApplicationContext().getSystemService(Context
                .WINDOW_SERVICE);
        int w = WindowManager.LayoutParams.WRAP_CONTENT;
        int h = WindowManager.LayoutParams.WRAP_CONTENT;
        int type = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        int flags = 0;
        if (outsideTouchable()){
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        int format = PixelFormat.TRANSLUCENT;
        mLayoutParams = new WindowManager.LayoutParams(w, h, type, flags, format);
        mLayoutParams.gravity = Gravity.CENTER;
        mContentView = view;
        setOnTouchListener(getOnTouchListener());
    }

    protected abstract View.OnTouchListener getOnTouchListener();
    protected abstract boolean outsideTouchable();

    protected boolean isDismissed = true;

    public void show() {
        if (mWM != null && mContentView != null && isDismissed) {
            mWM.addView(mContentView, mLayoutParams);
            isDismissed = false;
        }
    }

    public void dismiss() {
        if (mWM != null && mContentView != null && !isDismissed) {
            mWM.removeView(mContentView);
            isDismissed = true;
        }
    }

    public void setOnclickListener(View.OnClickListener listener) {
        mContentView.setOnClickListener(listener);
    }

    public void setOnTouchListener(View.OnTouchListener listener) {
        mContentView.setOnTouchListener(listener);
    }

    public void setContentView(View view) {
        mContentView = view;
    }


    public View getContentView() {
        return mContentView;
    }


}
