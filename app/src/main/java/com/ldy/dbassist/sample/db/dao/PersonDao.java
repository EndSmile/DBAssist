package com.ldy.dbassist.sample.db.dao;


import android.database.Cursor;

import com.ldy.dbassist.sample.db.model.Person;
import com.ldy.dbassist.sample.db.model.PersonAssistDao;

import java.util.List;

/**
 * Created by ldy on 2017/5/12.
 */

public class PersonDao extends PersonAssistDao {

    private static final PersonDao instance = new PersonDao();

    private PersonDao() {
    }

    public static PersonDao getInstance() {
        return instance;
    }

    public void save(final List<Person> personList) {
        if (personList == null || personList.isEmpty()) {
            return;
        }
        transaction(new Runnable() {
            @Override
            public void run() {
                for (Person person : personList) {
                    database.insert(TABLE_NAME, null, transform2ContentValues(person));
                }
            }
        });
    }

    public List<Person> findAll() {
        Cursor cursor = database.rawQuery(replace("select * from ?", TABLE_NAME), null);
        return cursorTransform(cursor);
    }

    public void delete() {
        database.delete(TABLE_NAME, null, null);
    }
}
