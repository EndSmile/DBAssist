package com.ldy.dbassist.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by ldy on 2017/4/5.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

}
