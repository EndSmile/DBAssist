package com.ldy.dbassist.sample.db;


import android.database.sqlite.SQLiteDatabase;

import com.ldy.dbassist.sample.db.dao.DepartmentDao;
import com.ldy.dbassist.sample.db.dao.PersonDao;
import com.ldy.dbassist.sample.db.model.Department;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ldy on 2017/5/15.
 */

public class DBService {
    private static final DBService instance = new DBService();
    private PersonDao personDao;
    private DepartmentDao departmentDao;

    private DBService() {
        database = DBCreator.instance().getWritableDatabase();

    }

    public static DBService getInstance() {
        return instance;
    }

    protected final SQLiteDatabase database;

    public void init(long userId) {
        departmentDao = new DepartmentDao("department_" + userId);
        departmentDao.createTable();
        personDao = PersonDao.getInstance();
    }

    public void saveDepAndPerson(final Department department) {
        transaction(new Runnable() {
            @Override
            public void run() {
                departmentDao.save(Arrays.asList(department));
                personDao.save(department.getPersonList());
            }
        });
    }

    public List<Department> findAllDepAndPerson() {
        List<Department> departmentList = departmentDao.findAll();
        for (Department department :
                departmentList) {
            department.setPersonList(personDao.findFromDep(department.getId()));
        }
        return departmentList;
    }

    private void transaction(Runnable runnable) {
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

}
