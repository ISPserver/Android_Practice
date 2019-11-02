package com.example.surface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class CircleDrawer implements Runnable {
    private int x;
    private int y;
    private  boolean isRed;
    private long period;
    private SurfaceHolder holder;

    public CircleDrawer(int x, int y, boolean isRed, long period, SurfaceHolder holder){
        this.x = x;
        this.y = y;
        this.isRed = isRed;
        this.period = period;
    }

    @Override
    public void run() {
        while (true){
            Canvas o = null;
            try{
                o = holder.lockCanvas();
                //한 쓰레드만 holder를 잡을 수 있다.
                synchronized (holder){
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(20);
                    o.drawCircle(x,y,50,paint);
                }
            }
            finally {
                if(o != null) holder.unlockCanvasAndPost(o);
            }
            try{
                Thread.sleep(period);
            }catch (Exception e){

            }
        }
    }
}
