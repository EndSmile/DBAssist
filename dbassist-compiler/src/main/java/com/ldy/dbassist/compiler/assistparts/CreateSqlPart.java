package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.annotations.PrimaryKey;
import com.ldy.dbassist.compiler.assistparts.base.ColumnType;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by ldy on 2017/5/11.
 * 添加创建表的sql语句的方法
 */
public class CreateSqlPart extends com.ldy.dbassist.compiler.assistparts.base.ABAssistPart {

    public CreateSqlPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        StringBuilder createSql = null;

        List<? extends Element> enclosedElements = tabElement.getEnclosedElements();
        for (int i = 0, length = enclosedElements.size(); i < length; i++) {
            Element childElement = enclosedElements.get(i);
            if (!isColumn(childElement)) {
                continue;
            }

            if (createSql == null) {
                createSql = new StringBuilder(" (");
            } else {
                createSql.append(",");
            }
            String columnName;
            columnName = getColumnName(childElement);

            TypeMirror typeMirror = childElement.asType();
            switch (ColumnType.valueOf(typeMirror)) {
                case BOOLEAN:
                    createSql.append(columnName).append(" BLOB");
                    break;
                case INT:
                case LONG:
                case SHORT:
                    createSql.append(columnName).append(" INTEGER");
                    break;
                case FLOAT:
                case DOUBLE:
                    createSql.append(columnName).append(" REAL");
                    break;
                case STRING:
                    createSql.append(columnName).append(" TEXT");
                    break;
                case UNKNOWN:
                    error(childElement, "%s is a unknown type", typeMirror);
            }
            PrimaryKey primaryKey = childElement.getAnnotation(PrimaryKey.class);

            if (primaryKey != null) {
                createSql.append(" PRIMARY KEY");
                if (primaryKey.autoincrement()) {
                    createSql.append(" AUTOINCREMENT");
                }
            }
        }

        if (createSql != null) {
            createSql.append(");");
            String createSqlStr = createSql.toString();
            MethodSpec.Builder builder = MethodSpec.methodBuilder("getCreateSql")
                    .addModifiers(PUBLIC)
                    .returns(TypeVariableName.get("String"))
                    .addStatement("return $S+$L+$S", "CREATE TABLE IF NOT EXISTS ",
                            getTabNameVariable(tabElement).getVarName(), createSqlStr);
            if (getTabNameVariable(tabElement).isConstant) {
                builder.addModifiers(STATIC);
            }

            MethodSpec createTabMethod = builder
                    .build();
            typeSpecBuilder.addMethod(createTabMethod);
        }
    }

}
