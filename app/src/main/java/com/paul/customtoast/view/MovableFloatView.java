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
import android.view.MotionEvent;
import android.view.View;

/**
 * 一个可以移动的浮动于其他应用之上的View.
 * Created by Paul Chang on 2015/11/19.
 */
public class MovableFloatView extends FloatView {
    private static final String TAG = "MovableFloatView";
    private static final boolean DEBUG = false;

    final class OnMovableTouchListener implements View.OnTouchListener {
        private int downX;
        private int downY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX = (int) event.getRawX();
                    int moveY = (int) event.getRawY();

                    int dimX = moveX - downX;
                    int dimY = moveY - downY;
                    mLayoutParams.x += dimX;
                    mLayoutParams.y += dimY;

                    mWM.updateViewLayout(mContentView, mLayoutParams);

                    downX = moveX;
                    downY = moveY;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    public MovableFloatView(Context context, View view) {
        super(context, view);
    }

    @Override
    public View.OnTouchListener getOnTouchListener() {
        return new OnMovableTouchListener();
    }

    @Override
    protected boolean outsideTouchable() {
        return true;
    }
}
