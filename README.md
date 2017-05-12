# DBAssist
通过apt生成dao层的帮助类，生成原生操作中繁重重复的代码，但并不直接操作数据库。

非常小巧，只是帮助生成assist类，只需要依赖一个只在编译期生效的注解库

通过对Moudle类进行简单的注解，生成一个dao层的assist类，用户可以继承这个assist类，assist类中会生成大量繁重的代码，继承者就可以只关心业务逻辑了

目前包括的有：
1. 表名，列名生成，且支持将表名设为变量
2. 生成创建数据库sql语句的函数
3. Module转换为ContentValues函数，且支持自定义列
4. cursor转换为moduleList函数，且支持自定义列
5. 为生成类指定父类

**Module类**
```
@Table(inherit = BaseDao.class)
public class Person {
    @PrimaryKey
    long id;
    String code;
    String name;
    boolean isMale;
    Integer age;
}

```
**生成的类展示**
```
// Generated code from DBAssist. Do not modify!

public class PersonAssistDao extends com.ldy.dbassist.sample.db.dao.BaseDao {
    public static final String TABLE_NAME = "Person";

    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String IS_MALE = "isMale";

    public static final String AGE = "age";

    public static String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY,code TEXT,name TEXT,isMale BLOB,age INTEGER);";
    }

    /**
     * 取出person中的值并返回ContentValues，columns为null时则默认取出所有数据库对应的所有列
     */
    public static android.content.ContentValues transform2ContentValues(com.ldy.dbassist.sample.db.model.Person person, String... columns) {
        android.content.ContentValues values = new android.content.ContentValues();
        if (person == null) {
            return values;
        }
        ;
        if (columns == null || columns.length == 0) {
            values.put(ID, person.id);
            values.put(CODE, person.code);
            values.put(NAME, person.name);
            values.put(IS_MALE, person.isMale);
            values.put(AGE, person.age);
        } else {
            for (String column : columns) {
                if (ID.equals(column)) {
                    values.put(ID, person.id);
                }
                if (CODE.equals(column)) {
                    values.put(CODE, person.code);
                }
                if (NAME.equals(column)) {
                    values.put(NAME, person.name);
                }
                if (IS_MALE.equals(column)) {
                    values.put(IS_MALE, person.isMale);
                }
                if (AGE.equals(column)) {
                    values.put(AGE, person.age);
                }
            }
        }
        return values;
    }

    /**
     * 取出cursor中的值并生成list，columns为null时则默认取出cursor中的全部列</p>
     * 注意:操作结束后cursor会被关闭
     */
    public static java.util.List<com.ldy.dbassist.sample.db.model.Person> cursorTransform(android.database.Cursor cursor, String... columns) {
        java.util.List<com.ldy.dbassist.sample.db.model.Person> list = new java.util.ArrayList<>();
        while (cursor.moveToNext()) {
            com.ldy.dbassist.sample.db.model.Person person = new com.ldy.dbassist.sample.db.model.Person();
            if (columns == null || columns.length == 0) {
                person.id = cursor.getLong(cursor.getColumnIndex(ID));
                person.code = cursor.getString(cursor.getColumnIndex(CODE));
                person.name = cursor.getString(cursor.getColumnIndex(NAME));
                person.isMale = cursor.getInt(cursor.getColumnIndex(IS_MALE)) != 0;
                person.age = cursor.getInt(cursor.getColumnIndex(AGE));
            } else {
                for (String column : columns) {
                    if (ID.equals(column)) {
                        person.id = cursor.getLong(cursor.getColumnIndex(ID));
                    }
                    if (CODE.equals(column)) {
                        person.code = cursor.getString(cursor.getColumnIndex(CODE));
                    }
                    if (NAME.equals(column)) {
                        person.name = cursor.getString(cursor.getColumnIndex(NAME));
                    }
                    if (IS_MALE.equals(column)) {
                        person.isMale = cursor.getInt(cursor.getColumnIndex(IS_MALE)) != 0;
                    }
                    if (AGE.equals(column)) {
                        person.age = cursor.getInt(cursor.getColumnIndex(AGE));
                    }
                }
            }
            list.add(person);
        }
        cursor.close();
        return list;
    }
}
```