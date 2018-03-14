package com.example.guodazhao.voicefloatdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by guodazhao on 2017/5/19 0019.
 */

public class VoiceFloat extends FloatView implements SuspendingView.IFloatPosition {
    private Context mContext;
    private String Ndaction;
    private SuspendingView voiceFloatView=null;

    private View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (voiceFloatView!=null&&voiceFloatView.isCoverState()) {
                voiceFloatView.reverse();
                return;
            }
            if (mContext instanceof Activity) {

            }
        }
    };
    private ViewHolder holder;
    private MediaPlayerReceiver receiver=new MediaPlayerReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MediaPlayerReceiver.ACTION_BOOK_PROGRESS)) {
                String bookId=intent.getStringExtra(KEY_BOOK_ID);
                String ndaction=intent.getStringExtra(KEY_ACTION);
                if (!TextUtils.isEmpty(ndaction)) {
                    Ndaction=ndaction;
                    String img = intent.getStringExtra(KEY_IMG);
                    String nookName = intent.getStringExtra(KEY_BOOK_NAME);
                    int now = intent.getExtras().getInt(KEY_NOW_TIME, 0);
                    int total = intent.getExtras().getInt(KEY_TOTAL_TIME, 0);
                    if (holder==null) {

                    }
                }
            }
        }
    };
    @Override
    public int getFloatCoverOffset() {
        return 0;
    }

    @Override
    public int getFloatY() {
        return 0;
    }

    class ViewHolder{
        private CircleImageView header;
        private TextView book_name , progress;
        private ClipDrawable clipDrawable;
        // pullover;
        String img,bookname;

        public ViewHolder(View view) {
            header = (CircleImageView) view.findViewById(R.id.header_img);
            header.setBorderWidth(2);
           // header.setOval(true);
         //   header.setBorderColor(ColorStateList.valueOf(Color.WHITE);
            book_name = (TextView) view.findViewById(R.id.book_name);
            progress = (TextView) view.findViewById(R.id.string_pro);
            clipDrawable = (ClipDrawable) ((LayerDrawable)view.findViewById(R.id.bg_pro).getBackground()).findDrawableByLayerId(R.id.clip);
        }

    }
}
