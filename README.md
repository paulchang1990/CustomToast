# CustomToast

### 简介
  使用WindowManager添加类似Toast效果浮动于应用之上的View，该view可以设定移动或是固定。
  
### 参考
- [android-UCToast](https://github.com/liaohuqiu/android-UCToast)

### 说明
1. 使用`WindowManager`来添加要显示的`View`，添加时需要传入一个参数`WindowManager.LayoutParams`来说明该`View`如何布局。
2. `LayoutParams中`几个参数
  - type:该View是什么类型，使用`TYPE_TOAST`不需要申请权限但是必须在API>=19才可以响应事件；在API<19时，可以使用
  `TYPE_PHONE`或者`TYPE_SYSTEM_ALERT`，但需要权限`android.permission.SYSTEM_ALERT_WINDOW`.
  - flags:设置为0代表可以点击或触摸但是不可以将事件向其覆盖着的应用进行传递；若要将事件向背后传递则可以使用
  `WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE`或`FLAG_NOT_TOUCH_MODAL`，区别在于前者不可以接收按键事件
  - format:`PixelFormat.TRANSLUCENT`代表半透明
  - width:如`WRAP_CONTENT`或`MATCH_PARENT`等
  - height:同上
3. 相关的代码片段
  - 初始化WindowManager
  ```java
  mWM = (WindowManager) context.getApplicationContext().getSystemService(Context
        .WINDOW_SERVICE);
  int w = WindowManager.LayoutParams.WRAP_CONTENT;
  int h = WindowManager.LayoutParams.WRAP_CONTENT;
  int type = 0;
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//判断系统版本
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
  ```
  - 显示该View
  ```java
  mWM.addView(view,mLayoutParams);
  ```
  - 移除该View
  ```java
  mWM.removeView(view);
  ```
  - 改变该View的显示位置
  ```java
  //判断Touch事件，修改LayoutParams.x和LayoutParams.y的值，dx和dy分别表示移动的偏移量
  mLayoutParams.x += dx;
  mLayoutParams.y += dy;
  mWM.updateViewLayout(view,mLayoutParams);
  ```
  - 在Touch事件监听回调中，判断触摸或点击的区域是否为View所在区域
  ```java
  int x = (int) event.getX();
  int y = (int) event.getY();
  Rect rect = new Rect();
  mContentView.getGlobalVisibleRect(rect);
  if (!rect.contains(x, y)) {
      //TODO outside action
  } 
  ```
4. Demo图示

  <img src='https://github.com/paulchang1990/CustomToast/blob/master/captures/screen%20shot.gif' width=300 hight=500 aligh='center'/>
  
### Tips
1. 动态添加布局时，使用的`LayoutParams`是让父类视图来决定如何布局子视图的，如向`WindowManger`中添加布局`FrameLayout`时，该布局对应的应该
为`WindowManager.LayoutParams`；向`FrameLayout`中添加一个`View`时，该`View`应该使用`FrameLayout.LayoutParams`参数
2. `MotionEvent.getX()`和`getRawX()`：前者代表触摸点相对于被触摸的`View`左上点的坐标值，后者代表相对于整个屏幕左上点的坐标值

## License
<pre><code>Copyright 2015 Paul Chang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</code></pre>