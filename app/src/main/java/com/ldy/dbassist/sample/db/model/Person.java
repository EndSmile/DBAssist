package com.ldy.dbassist.sample.db.model;

import com.ldy.dbassist.annotations.PrimaryKey;
import com.ldy.dbassist.annotations.Table;
import com.ldy.dbassist.sample.db.dao.BaseDao;

/**
 * Created by ldy on 2017/5/11.
 */
@Table(inherit = BaseDao.class)
public class Person {
    @PrimaryKey
    long id;
    String code;
    String name;
    boolean isMale;
    Integer age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (isMale != person.isMale) return false;
        if (code != null ? !code.equals(person.code) : person.code != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return age != null ? age.equals(person.age) : person.age == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isMale ? 1 : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", isMale=" + isMale +
                ", age=" + age +
                '}';
    }
}
