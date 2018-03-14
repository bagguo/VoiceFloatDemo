package com.example.guodazhao.voicefloatdemo;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by guodazhao on 2017/5/19 0019.
 */

public class SuspendingView extends LinearLayout {
    //线程休眠 ms数 30/内核数+1
    private final long SLEEP = 30L / (Runtime.getRuntime().availableProcessors() + 1);
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowMangerParams;
    private Context mContext;
    private IFloatPosition mIFloatPosition;
    Scroller mScroller;
    private OnClickListener mClickListener;

    private float x;//悬浮窗起始位置
    private float y;

    private float mTouchX;
    private float mTouchY;

    private float mStartX;
    private float mStartY;
    private boolean needToWait;
    private final float FACTOR = 2f;
    private boolean isThreadSleep = false;//选择 是选择性能调用  还是 线程休眠调用

    public SuspendingView(Context context, WindowManager windowManager, WindowManager.LayoutParams windowMangerParams
            , IFloatPosition iFloatPosition) {
        super(context);
        mContext = context;
        mWindowManager = windowManager;
        mWindowMangerParams = windowMangerParams;
        mScroller = new Scroller(context);
        this.mIFloatPosition = iFloatPosition;
    }

    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
        super.setOnClickListener(l);
    }

    private void updateViewPosition() {
        mWindowMangerParams.x = (int) (x - mTouchX);
        mWindowMangerParams.y = (int) (y - mTouchY);
        mWindowManager.updateViewLayout(this, mWindowMangerParams);
    }

    //响应
    public synchronized void notifys() {
        if (needToWait) {
            needToWait = false;
            try {
                this.notify();
            } catch (Exception e) {
            }
        }
    }

    public boolean getIsWait() {
        return needToWait;
    }

    public void setWait(boolean wait) {
        needToWait = wait;
    }

    /**
     * 悬浮窗半隐藏
     */
    public synchronized void cover() {

        if (!(getVisibility() == View.VISIBLE)) {
            return;
        }

        if (mScroller.computeScrollOffset()) {
            return;
        }

        if (isCoverState()) {
            return;
        }

        int finalX = 0;
        int finalY = 0;
        if (mIFloatPosition != null) {
            finalX = -mIFloatPosition.getFloatCoverOffset();//半隐藏时位置
            finalY = mIFloatPosition.getFloatY();
        }
        animation(finalX, finalY);
    }

    public synchronized void reverse(){
        if (mScroller.computeScrollOffset()) {
            return;
        }

        if (!isCoverState()) {
            return;
        }
        int finalX=0;
        int finalY=0;
        if (mIFloatPosition!=null) {
            finalX=0;
            finalY = mIFloatPosition.getFloatY();
        }
        animation(finalX,finalY);
    }
    private void animation(int finalX, int finalY) {
        int dx = finalX - mWindowMangerParams.x;
        int dy = finalY - mWindowMangerParams.y;
        int pass = (int) (Math.abs(dx) + Math.abs(dy) * FACTOR);

        mScroller.startScroll(mWindowMangerParams.x, mWindowMangerParams.y, dx, dy, pass);
        postRunner();
    }

    public void coverNotAnimate(){
        int finalX=0;
        int finalY=0;

        if (mIFloatPosition!=null) {
            finalX = -mIFloatPosition.getFloatCoverOffset();
            finalY = mIFloatPosition.getFloatY();
        }
        updataViewPosition(finalX,finalY,null);
    }
    /**
     * 浮窗滚动过程中更新位置
     */
    public void postRunner() {
        new Thread() {
            @Override
            public void run() {
                while (mScroller.computeScrollOffset()) {
                    if (isThreadSleep) {
                        updatePosition(null);
                    } else {
                        updatePosition();
                    }
                }
            }
        }.start();
    }

    public synchronized void updatePosition() {
        final int x = mScroller.getCurrX();
        final int y = mScroller.getCurrY();

        SuspendingView.this.post(new Runnable() {
            @Override
            public void run() {
                updataViewPosition(x, y);
            }
        });

        try {
            SuspendingView.this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void updatePosition(final Object f) {
        final int x = mScroller.getCurrX();
        final int y = mScroller.getCurrY();

        SuspendingView.this.post(new Runnable() {
            @Override
            public void run() {
                updataViewPosition(x, y, f);
            }
        });

        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updataViewPosition(int x, int y) {
        mWindowMangerParams.x = x;
        mWindowMangerParams.y = y;
        try {

            mWindowManager.updateViewLayout(this, mWindowMangerParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.notifyAll();
    }

    private void updataViewPosition(int x, int y, Object f) {
        updataViewPosition(x, y);
    }

    public boolean isCoverState() {
        return mWindowMangerParams.x < 0;
    }

    //获取悬浮位置
    public interface IFloatPosition {
        int getFloatCoverOffset();//悬浮半隐藏位移

        int getFloatY();
    }
}
