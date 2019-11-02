package com.example.surface;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Myview extends SurfaceView implements SurfaceHolder.Callback {
    public Myview(Context context) {
        super(context);
        //이것을 수행해야 아래의 3개의 콜백 메소드가 실제로 동작한다.
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //캔버스를 갖고있는 holder를 얻었음.
        CircleDrawer r1 = new CircleDrawer(100,100,true,200,holder);
        Thread t1 = new Thread(r1);
        t1.start();

        CircleDrawer r2 = new CircleDrawer(500,100,true,300,holder);
        Thread t2 = new Thread(r2);
        t2.start();

        CircleDrawer r3 = new CircleDrawer(100,500,true,500,holder);
        Thread t3 = new Thread(r3);
        t3.start();

        CircleDrawer r4 = new CircleDrawer(500,500,true,700,holder);
        Thread t4 = new Thread(r4);
        t4.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
