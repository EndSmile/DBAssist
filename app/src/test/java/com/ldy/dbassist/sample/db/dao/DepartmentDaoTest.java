package com.ldy.dbassist.sample.db.dao;

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
 * Created by ldy on 2017/5/12.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DepartmentDaoTest {

    private DepartmentDao departmentDao;

    @Before
    public void setup() throws Exception {
        Class<App> appClass = App.class;
        App app = appClass.newInstance();
        Field field = appClass.getDeclaredField("context");
        field.setAccessible(true);
        field.set(app,RuntimeEnvironment.application);
        departmentDao = new DepartmentDao("department_test");
        departmentDao.createTable();
    }

    @Test
    public void save() throws Exception {
        departmentDao.delete();
        Department department = new Department();
        department.setId(1);
        department.setName("test");

        departmentDao.save(Arrays.asList(department));
        List<Department> all = departmentDao.findAll();
        assertEquals(1,all.size());
        assertEquals(department,all.get(0));
    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}