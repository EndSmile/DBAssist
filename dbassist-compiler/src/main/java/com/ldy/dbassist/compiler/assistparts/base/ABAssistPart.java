package com.ldy.dbassist.compiler.assistparts.base;

import com.ldy.dbassist.annotations.Column;
import com.ldy.dbassist.annotations.NonColumn;
import com.ldy.dbassist.annotations.PrimaryKey;
import com.ldy.dbassist.annotations.Table;
import com.ldy.dbassist.compiler.Utils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by ldy on 2017/5/11.
 */

public abstract class ABAssistPart implements IAddPart {
    protected final Messager messager;

    public ABAssistPart(Messager messager) {
        this.messager = messager;
    }

    public void error(Element e, String msg, Object... args) {
        Utils.error(messager, e, msg, args);
    }

    public static String getTabName(TypeElement tabElement) {
        Table annotation = tabElement.getAnnotation(Table.class);
        String tabName;
        if (annotation.value().isEmpty()) {
            tabName = tabElement.getSimpleName().toString();
        } else {
            tabName = annotation.value();
        }
        return tabName;
    }

    protected static boolean isColumn(Element element) {
        if (!isField(element)) {
            return false;
        }

        Table table = element.getEnclosingElement().getAnnotation(Table.class);
        if (table != null) {
            if (table.allFields()) {
                return element.getAnnotation(NonColumn.class) == null;
            } else {
                return element.getAnnotation(Column.class) != null
                        || element.getAnnotation(PrimaryKey.class) != null;
            }
        }
        return false;
    }

    protected static boolean isField(Element element) {
        return element.getKind() == ElementKind.FIELD;
    }

    protected static String getColumnName(Element childElement) {
        String columnName;
        Column column = childElement.getAnnotation(Column.class);
        PrimaryKey primaryKey = childElement.getAnnotation(PrimaryKey.class);
        if (column != null && !Utils.empty(column.value())) {
            columnName = column.value();
        } else if (primaryKey != null && !Utils.empty(primaryKey.value())) {
            columnName = primaryKey.value();
        } else {
            columnName = childElement.getSimpleName().toString();
        }
        return columnName;
    }


    protected static TabNameVariable getTabNameVariable(Element tabElement) {
        return new TabNameVariable(tabElement);
    }

    public static class TabNameVariable {
        public final String varNameValue;
        public final boolean isConstant;

        public TabNameVariable(Element element) {
            Table table = element.getAnnotation(Table.class);
            if (table == null || Utils.empty(table.value())) {
                varNameValue = element.getSimpleName().toString();
                isConstant = table != null && table.isConstantName();
            } else {
                varNameValue = table.value();
                isConstant = table.isConstantName();
            }
        }

        public String getVarName() {
            return isConstant ? "TABLE_NAME" : "tableName";
        }
    }
}
