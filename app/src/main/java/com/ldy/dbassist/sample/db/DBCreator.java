package com.ldy.dbassist.sample.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldy.dbassist.sample.App;
import com.ldy.dbassist.sample.db.dao.PersonDao;
import com.ldy.dbassist.sample.db.model.DepartmentDaoAssist;
import com.ldy.dbassist.sample.db.model.PersonDaoAssist;


/**
 * Created by ldy on 2017/4/10.
 */

public class DBCreator extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database";

    private DBCreator() {
        super(App.getContext(), DB_NAME, null, DB_VERSION);
    }

    private static class Instance {
        private static final DBCreator dbCreator = new DBCreator();
    }

    public static DBCreator instance() {
        return Instance.dbCreator;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonDaoAssist.getCreateSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
