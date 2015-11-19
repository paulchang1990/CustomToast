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

package com.paul.customtoast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.paul.customtoast.view.MovableFloatView;
import com.paul.customtoast.view.StableFloatView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = false;
    private MovableFloatView mMovableView;
    private StableFloatView mStableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_show_stable).setOnClickListener(this);
        findViewById(R.id.btn_show_move).setOnClickListener(this);
        findViewById(R.id.btn_dismiss_move).setOnClickListener(this);
        View movableContentView = View.inflate(this, R.layout.floatview_content, null);
        View stableContentView = View.inflate(this, R.layout.floatview_content, null);

        mMovableView = new MovableFloatView(this, movableContentView);
        mStableView = new StableFloatView(this, stableContentView);
        mStableView.setOnTouchOutsideListener(new StableFloatView.OnTouchOutsideListener() {
            @Override
            public void onOutsideTouch(View v, MotionEvent event) {
                mStableView.dismiss();
            }
        });

        mStableView.setOnTouchInsideListener(new StableFloatView.onTouchInsideListener() {
            @Override
            public void onInsideTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "touched here!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_show_stable) {
            mStableView.show();
        } else if (id == R.id.btn_show_move) {
            mMovableView.show();
        } else if (id == R.id.btn_dismiss_move) {
            mMovableView.dismiss();
        }
    }

}
