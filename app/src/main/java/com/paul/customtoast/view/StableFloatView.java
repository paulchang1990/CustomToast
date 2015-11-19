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
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 一个可以响应内外点击的浮动于其他应用之上的View.
 * Created by Paul Chang on 2015/11/19.
 */
public class StableFloatView extends FloatView {

    private static final String TAG = "StableFloatView";
    private static final boolean DEBUG = false;
    private OnTouchOutsideListener mOutsideListener;
    private onTouchInsideListener mInsideListener;

    final class OnStableTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (DEBUG) {
                Log.d(TAG, "touch (x,y)=("+x+","+y+")");
            }
            Rect rect = new Rect();
            mContentView.getGlobalVisibleRect(rect);
            if (!rect.contains(x, y)) {
                if (mOutsideListener != null) {
                    mOutsideListener.onOutsideTouch(v, event);
                }
            } else {
                if (mInsideListener != null) {
                    mInsideListener.onInsideTouch(v, event);
                }
            }
            return false;
        }
    }

    public StableFloatView(Context context, View view) {
        super(context, view);
    }

    @Override
    public View.OnTouchListener getOnTouchListener() {
        return new OnStableTouchListener();
    }

    @Override
    protected boolean outsideTouchable() {
        return false;
    }

    public void setOnTouchOutsideListener(OnTouchOutsideListener listener) {
        this.mOutsideListener = listener;
    }

    public void setOnTouchInsideListener(onTouchInsideListener listener) {
        this.mInsideListener = listener;
    }

    public interface OnTouchOutsideListener {
        void onOutsideTouch(View v, MotionEvent event);
    }

    public interface onTouchInsideListener {
        void onInsideTouch(View v, MotionEvent event);
    }
}
