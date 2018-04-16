package com.l.lookandtake;

import android.app.Application;

import com.l.lookandtake.util.Utils;

/**
 * Created by L on 2018/4/11.
 * Description:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
