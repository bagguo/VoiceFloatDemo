package com.example.guodazhao.voicefloatdemo;

/**
 * Created by guodazhao on 2017/5/19 0019.
 */

public class FloatView {
    public int floatOffsetX,floatOffsetY;
    public boolean isHide;

    public void hideFloatCurActivity(){
        isHide=true;
    }
    public void showFloat(){
        isHide=false;
    }
}
