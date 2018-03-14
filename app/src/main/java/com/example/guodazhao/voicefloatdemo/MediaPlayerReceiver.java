package com.example.guodazhao.voicefloatdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by guodazhao on 2017/5/19 0019.
 * 有声媒体播放状态变化的消息通知
 * play pause
 * bookid
 */

public abstract class MediaPlayerReceiver extends BroadcastReceiver{

    public  static final String ACTION_BOOK_MEDIA_PLAYING="com.changdu.media.play";
    public static final String ACTION_BOOL_MEDIA_PAUSE = "com.changdu.media.pause";
    public  static final String ACTION_BOOK_PROGRESS = "com.changdu.media.progress";


    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";
    public static final String KEY_IS_VOICE = "IS_VOICE";
    public static final String KEY_IMG = "IMG";
    public static final String KEY_BOOK_NAME = "book_name";
    public static final String KEY_NOW_TIME = "now_time";
    public static final String KEY_TOTAL_TIME = "total_time";
    public static final String KEY_ACTION = "NDACTION";

    public static void firePlayingEvent(Context context, String bookId){
        firePlayingEvent(context,bookId,false);
    }

    public static void firePlayingEvent(Context context, String bookId, boolean isVoice) {
        Intent intent=new Intent(ACTION_BOOK_MEDIA_PLAYING);
        intent.putExtra(KEY_BOOK_ID,bookId);
        if (isVoice) {
            intent.putExtra(KEY_IS_VOICE,"1");
        }
        context.sendBroadcast(intent);
    }

    public static void firePauseEvent(Context context,boolean isVoice){
        Intent intent = new Intent(ACTION_BOOL_MEDIA_PAUSE);
        if (isVoice) {
            intent.putExtra(KEY_IS_VOICE, "1");
        }
        context.sendBroadcast(intent);
    }
    public static void firePauseEvent(Context context){
        firePauseEvent(context,false);
    }
    public static void fireProgressEvent(Context context,String bookId,String bookName,String imgUrl
            ,String ndaction,int nowTime,int totalTime){
        Intent intent=new Intent(ACTION_BOOK_PROGRESS);
        if (!TextUtils.isEmpty(bookId)) {
            intent.putExtra(KEY_BOOK_ID, bookId);
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            intent.putExtra(KEY_IMG, imgUrl);
        }
        if (!TextUtils.isEmpty(bookName)) {
            intent.putExtra(KEY_BOOK_NAME, bookName);
        }
        if (!TextUtils.isEmpty(ndaction)){
            intent.putExtra(KEY_ACTION , ndaction);
        }
        intent.putExtra(KEY_NOW_TIME, nowTime);
        intent.putExtra(KEY_TOTAL_TIME, totalTime);
        context.sendBroadcast(intent);
    }
}
