package com.ldy.dbassist.compiler.assistparts.base;

import javax.lang.model.type.TypeMirror;

/**
 * Created by ldy on 2017/5/12.
 */

public enum ColumnType {
    BOOLEAN, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, UNKNOWN;

    public static ColumnType valueOf(TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case BOOLEAN:
                return BOOLEAN;
            case SHORT:
                return SHORT;
            case INT:
                return INT;
            case LONG:
                return LONG;
            case FLOAT:
                return FLOAT;
            case DOUBLE:
                return DOUBLE;
            case DECLARED:
                String typeName = typeMirror.toString();
                if (typeName.equals(String.class.getName())) {
                    return STRING;
                } else if (typeName.equals(Boolean.class.getName())) {
                    return BOOLEAN;
                } else if (typeName.equals(Short.class.getName())) {
                    return SHORT;
                } else if (typeName.equals(Integer.class.getName())) {
                    return INT;
                } else if (typeName.equals(Long.class.getName())) {
                    return LONG;
                } else if (typeName.equals(Float.class.getName())) {
                    return FLOAT;
                } else if (typeName.equals(Double.class.getName())) {
                    return DOUBLE;
                }
            default:
                return UNKNOWN;
        }
    }
}
