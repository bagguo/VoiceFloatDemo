package com.example.guodazhao.voicefloatdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guodazhao on 2017/5/19 0019.
 */

public class FloatViewController {

    private static FloatViewController instance;
    private List<FloatView> viewList;

    private FloatViewController() {
        viewList = new ArrayList<>();
    }

    public static FloatViewController getInstance(){
        if (instance==null) {
            synchronized (FloatViewController.class){
                instance = new FloatViewController();
            }
        }
        return  instance;
    }
    /**
     * 增加需要管理的floatview
     */
    public void addFloatView(FloatView viewFlow){
        if (viewList!=null&&viewFlow!=null) {
            viewList.add(viewFlow);
        }
    }

    /**
     * 隐藏悬浮窗
     */
public void hideFloatView(){
    if (viewList!=null) {
        for (FloatView v:viewList) {
            v.hideFloatCurActivity();
        }
    }
}
/**
 * 显示悬浮框
 */
public void showFloatView(){
    if (viewList!=null) {
        for (FloatView v : viewList) {
            v.showFloat();
        }
    }
}

public void delFloatView(FloatView v){
    if (viewList!=null) {
        if (viewList.contains(v)) {
            viewList.remove(v);
        }
    }
}
}

