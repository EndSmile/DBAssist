package com.ldy.dbassist.sample.db.model;

import com.ldy.dbassist.annotations.Column;
import com.ldy.dbassist.annotations.NonColumn;
import com.ldy.dbassist.annotations.PrimaryKey;
import com.ldy.dbassist.annotations.Table;
import com.ldy.dbassist.sample.db.dao.BaseDao;

import java.util.List;

/**
 * Created by ldy on 2017/5/12.
 * 假设你需要为每个用户单独建立一个部门表时，这时你可能需要用户登录后动态设置</p>
 * 这时需设置{@link Table#isConstantName()}为false，则生成的tableName为protect级变量，
 * 这时推荐自己的dao层继承生成的代码</p>
 * 例如:{@link com.ldy.dbassist.sample.db.dao.DepartmentDao}
 */
@Table(allFields = false,isConstantName = false,inherit = BaseDao.class)
public class Department {
    @PrimaryKey
    long id;
    @Column
    String name;
    List<Person> personList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personList=" + personList +
                '}';
    }
}
