package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.compiler.Utils;
import com.ldy.dbassist.compiler.assistparts.base.ColumnType;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by ldy on 2017/5/12.
 */
public class CursorTransformPart extends com.ldy.dbassist.compiler.assistparts.base.ABAssistPart {
    public static final String TYPE_VALUES = "android.database.Cursor";
    public static final String METHOD_NAME = "cursorTransform";

    public CursorTransformPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        //@Table对应的类的参数的参数名
        String className = Utils.uncapitalize(tabElement.getSimpleName().toString());
        String cursorParams = "cursor";
        String columnsName = "columns";
        String listName = "list";
        String listTypeStr = "java.util.List<" + tabElement.asType().toString() + ">";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(METHOD_NAME)
                .addModifiers(PUBLIC, STATIC)
                .addParameter(TypeVariableName.get(TYPE_VALUES)
                        , "cursor")
                .addParameter(TypeVariableName.get("String..."), columnsName)
                .returns(TypeVariableName.get(listTypeStr));

        builder.addStatement("$L $L = new java.util.ArrayList<>()", listTypeStr, listName);

        builder.addCode("while($L.moveToNext()){\n",cursorParams);
        builder.addStatement("$L $L = new $L()", tabElement.getQualifiedName(), className, tabElement.getQualifiedName());
        builder.addCode("if($L==null||$L.length==0){\n", columnsName,columnsName);
        for (Element childElement : tabElement.getEnclosedElements()) {
            if (!isColumn(childElement)) {
                continue;
            }
            addSetVar(className, cursorParams, builder, childElement);

        }
        builder.addCode("}else{\n");
        //当传入列名时，为传入的列单独赋值
        builder.addCode("for(String column:$L){\n", columnsName);
        for (Element childElement : tabElement.getEnclosedElements()) {
            if (!isColumn(childElement)) {
                continue;
            }
            builder.addCode("if($L.equals(column)){\n", Utils.constantTransform(getColumnName(childElement)));
            addSetVar(className, cursorParams, builder, childElement);
            builder.addCode("}\n");
        }
        builder.addCode("}\n");
        builder.addCode("}\n");
        builder.addStatement("$L.add($L)",listName,className);
        builder.addCode("}\n");
        builder.addStatement("$L.close()",cursorParams);
        builder.addStatement("return $L", listName);

        builder.addJavadoc("取出cursor中的值并生成list，$L为null时则默认取出cursor中的全部列</p>\n注意:操作结束后cursor会被关闭",columnsName);
        typeSpecBuilder.addMethod(builder.build());

    }

    private void addSetVar(String className, String cursorParams, MethodSpec.Builder builder, Element childElement) {
        //@Table 里的变量真实名称
        String varName = childElement.getSimpleName().toString();
        //@Table 里的变量对应的数据库名称
        String columnName = getColumnName(childElement);
        TypeMirror typeMirror = childElement.asType();
        String getStr = null;
        switch (ColumnType.valueOf(typeMirror)) {
            case BOOLEAN:
                builder.addStatement("$L.$L = $L.getInt($L.getColumnIndex($L))!=0", className, varName,
                        cursorParams, cursorParams, Utils.constantTransform(columnName));
                break;
            case INT:
                getStr = "getInt";
                break;
            case LONG:
                getStr = "getLong";
                break;
            case SHORT:
                getStr = "getShort";
                break;
            case FLOAT:
                getStr = "getFloat";
                break;
            case DOUBLE:
                getStr = "getDouble";
                break;
            case STRING:
                getStr = "getString";
                break;
            case UNKNOWN:
                error(childElement, "%s is a unknown type", typeMirror);
        }
        if (getStr != null) {
            builder.addStatement("$L.$L = $L.$L($L.getColumnIndex($L))", className, varName,
                    cursorParams, getStr, cursorParams, Utils.constantTransform(columnName));
        }
    }


}
