package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.compiler.Utils;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by ldy on 2017/5/12.
 */
public class Transform2ContentValuesPart extends ABAssistPart {
    public static final String TYPE_VALUES = "android.content.ContentValues";
    public static final String METHOD_NAME = "transform2ContentValues";

    public Transform2ContentValuesPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        String classParamsName = Utils.uncapitalize(tabElement.getSimpleName().toString());
        String columnsName="columns";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(METHOD_NAME)
                .addModifiers(PUBLIC, STATIC)
                .addParameter(TypeVariableName.get(tabElement.getQualifiedName().toString())
                        , classParamsName)
                .addParameter(TypeVariableName.get("String..."),columnsName)
                .returns(TypeVariableName.get(TYPE_VALUES));


        builder.addStatement("$L values = new $L()",TYPE_VALUES,TYPE_VALUES);
        builder.addStatement("if($L==null){return values;}",classParamsName);

        builder.addCode("if($L==null||$L.length==0){\n", columnsName,columnsName);
        for (Element childElement:tabElement.getEnclosedElements()) {
            if (!isColumn(childElement)) {
                continue;
            }

            String columnName = childElement.getSimpleName().toString();
            builder.addStatement("values.put($L,$L.$L)", Utils.constantTransform(getColumnName(childElement))
                    ,classParamsName,columnName);
        }
        builder.addCode("}else{\n");
        builder.addCode("for(String column:$L){\n",columnsName);
        for (Element childElement:tabElement.getEnclosedElements()) {
            if (!isColumn(childElement)) {
                continue;
            }
            String columnName = childElement.getSimpleName().toString();
            builder.addCode("if($L.equals(column)){\n", Utils.constantTransform(getColumnName(childElement)));
            builder.addStatement("values.put($L,$L.$L)", Utils.constantTransform(getColumnName(childElement))
                    ,classParamsName,columnName);
            builder.addCode("}\n");
        }
        builder.addCode("}\n");
        builder.addCode("}\n");
        builder.addStatement("return values");
        builder.addJavadoc("取出$L中的值并返回ContentValues，$L为null时则默认取出所有数据库对应的所有列",classParamsName,columnsName);
        typeSpecBuilder.addMethod(builder.build());
    }

}
