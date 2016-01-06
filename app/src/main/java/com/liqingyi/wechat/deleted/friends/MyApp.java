package com.liqingyi.wechat.deleted.friends;

import android.app.Application;
import android.content.Context;

import com.liqingyi.wechat.deleted.friends.util.VolleyClient;

/**
 * Created by liqin on 2016/1/5.
 */
public class MyApp extends Application{

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        VolleyClient.init(this);
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
