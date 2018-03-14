package com.example.guodazhao.voicefloatdemo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guodazhao on 2017/5/19 0019.
 */

public class OkHttpRequest {

    private static String reponseData;

    public static String netRequest(String url){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request=new Request.Builder()
                    .url(url)
                    .build();
            Response response= client.newCall(request).execute();
            reponseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reponseData;
    }
}
