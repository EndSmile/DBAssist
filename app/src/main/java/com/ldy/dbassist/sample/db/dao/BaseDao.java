package com.ldy.dbassist.sample.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.ldy.dbassist.sample.db.DBCreator;

/**
 * Created by ldy on 2017/5/12.
 */

public class BaseDao {
    protected final SQLiteDatabase database;

    public BaseDao() {
        database = DBCreator.instance().getWritableDatabase();
    }

    protected void transaction(Runnable runnable) {
        database.beginTransaction();
        try {
            runnable.run();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public static String replace(String s, String... strings) {
        for (String replace : strings) {
            s = s.replaceFirst("\\?", replace);
        }
        return s;
    }
}
