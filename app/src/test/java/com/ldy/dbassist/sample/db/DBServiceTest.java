package com.ldy.dbassist.sample.db;

import com.ldy.dbassist.BuildConfig;
import com.ldy.dbassist.sample.App;
import com.ldy.dbassist.sample.db.model.Department;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by ldy on 2017/5/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DBServiceTest {

    private DBService dbService;

    @Before
    public void init() throws Exception {
        Class<App> appClass = App.class;
        App app = appClass.newInstance();
        Field field = appClass.getDeclaredField("context");
        field.setAccessible(true);
        field.set(app, RuntimeEnvironment.application);

        dbService = DBService.getInstance();
        dbService.init(1011);
    }

    @Test
    public void saveDepAndPerson() throws Exception {

        Person person = new Person();
        person.setId(1);
        person.setCode("assist001");
        person.setName("liming");
        person.setAge(21);
        person.setMale(true);
        person.setDepId(1);

        Department department = new Department();
        department.setId(1);
        department.setName("test");
        department.setPersonList(Arrays.asList(person));
        dbService.saveDepAndPerson(department);

        List<Department> all = dbService.findAllDepAndPerson();
        assertEquals(1,all.size());
        assertEquals(department,all.get(0));
        assertEquals(person,all.get(0).getPersonList().get(0));
    }

}