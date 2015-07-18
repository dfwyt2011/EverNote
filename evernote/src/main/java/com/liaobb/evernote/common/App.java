package com.liaobb.evernote.common;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by liaobb on 2015/7/3.
 */
public class App extends LitePalApplication {

    public static final String TAG = "bill.lia";

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase db = Connector.getDatabase();//初始化数据库
    }
}