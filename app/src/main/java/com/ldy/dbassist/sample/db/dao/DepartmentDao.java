package com.ldy.dbassist.sample.db.dao;


import android.database.Cursor;

import com.ldy.dbassist.sample.db.model.Department;
import com.ldy.dbassist.sample.db.model.DepartmentDaoAssist;

import java.util.List;

/**
 * Created by ldy on 2017/5/15.
 */
public class DepartmentDao extends DepartmentDaoAssist {

    public DepartmentDao(String tableName) {
        this.tableName = tableName;
    }

    public void createTable(){
        database.execSQL(getCreateSql());
    }

    public void save(final List<Department> departmentList) {
        if (departmentList == null || departmentList.isEmpty()) {
            return;
        }
        transaction(new Runnable() {
            @Override
            public void run() {
                for (Department department : departmentList) {
                    database.insert(tableName, null, transform2ContentValues(department));
                }
            }
        });
    }

    public List<Department> findAll(){
        Cursor cursor = database.rawQuery(replace("select * from ?", tableName), null);
        return cursorTransform(cursor);
    }

    public void delete() {
        database.delete(tableName, null, null);
    }
}
