package com.ldy.dbassist.sample.db.dao;

import com.ldy.dbassist.BuildConfig;
import com.ldy.dbassist.sample.App;
import com.ldy.dbassist.sample.db.model.Person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ldy on 2017/5/12.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PersonDaoTest {

    private PersonDao personDao;

    @Before
    public void setup() throws Exception {
        Class<App> appClass = App.class;
        App app = appClass.newInstance();
        Field field = appClass.getDeclaredField("context");
        field.setAccessible(true);
        field.set(app,RuntimeEnvironment.application);
        personDao = PersonDao.getInstance();
    }

    @Test
    public void save() throws Exception {
        personDao.delete();
        Person person = new Person();
        person.setId(1);
        person.setCode("assist001");
        person.setName("liming");
        person.setAge(21);
        person.setMale(true);
        personDao.save(Arrays.asList(person));
        List<Person> all = personDao.findAll();
        assertEquals(1,all.size());
        assertEquals(person,all.get(0));
    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}