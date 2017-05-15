package com.ldy.dbassist.sample.db.dao;


import android.database.Cursor;

import com.ldy.dbassist.sample.db.model.Person;
import com.ldy.dbassist.sample.db.model.PersonDaoAssist;

import java.util.List;

/**
 * 可以使用静态导入帮助类的形式，快捷使用帮助类生成的代码
 */
import static com.ldy.dbassist.sample.db.model.PersonDaoAssist.*;

/**
 * Created by ldy on 2017/5/12.
 * <p>
 * 继承apt生成的类
 */
public class PersonDao extends BaseDao {

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

    public List<Person> findFromDep(long depId){
        Cursor cursor = database.rawQuery(replace("select * from ? where ?=?", TABLE_NAME,DEP_ID,
                String.valueOf(depId)), null);
        return cursorTransform(cursor);
    }

    public void delete() {
        database.delete(TABLE_NAME, null, null);
    }
}
