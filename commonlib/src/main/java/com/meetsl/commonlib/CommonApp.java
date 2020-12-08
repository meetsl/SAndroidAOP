package com.meetsl.commonlib;

import android.app.Application;

/**
 * Created by ShiLong on 2020/12/8.
 * <p>
 * desc: ""
 */
public class CommonApp extends Application {
    public static Application appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
