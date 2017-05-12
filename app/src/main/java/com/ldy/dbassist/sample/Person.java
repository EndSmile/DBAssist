package com.ldy.dbassist.sample;

import com.ldy.dbassist.annotations.Index;
import com.ldy.dbassist.annotations.PrimaryKey;
import com.ldy.dbassist.annotations.Table;

/**
 * Created by ldy on 2017/5/11.
 */
@Table(inherit = BaseDao.class,isConstantName = false)
public class Person {
    @PrimaryKey
    Long id;
    @Index(unique = true)
    String code;
    @Index
    String name;
    boolean isMan;

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

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }
}
