package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.annotations.Column;
import com.ldy.dbassist.annotations.NonColumn;
import com.ldy.dbassist.compiler.Utils;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by ldy on 2017/5/11.
 */

public class FiledPart extends ABAssistPart {

    public FiledPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        String tabName = getTabName(tabElement);
        TabNameVariable tabNameVariable = getTabNameVariable(tabElement);
        if (tabNameVariable.isConstant) {
            Utils.addStrConstant(typeSpecBuilder, tabNameVariable.getVarName(), tabName);
        } else {
            FieldSpec.Builder builder = FieldSpec.builder(TypeVariableName.get("String"),
                    tabNameVariable.getVarName()
                    , Modifier.PROTECTED)
                    .initializer("$S", tabName);
            typeSpecBuilder.addField(builder.build());
        }

        for (Element childElement : tabElement.getEnclosedElements()) {
            if (!isField(childElement)) {
                continue;
            }
            if (childElement.getAnnotation(NonColumn.class) != null) {
                continue;
            }

            String columnName;
            Column column = childElement.getAnnotation(Column.class);
            if (column == null || Utils.empty(column.value())) {
                columnName = childElement.getSimpleName().toString();
            } else {
                columnName = column.value();
            }
            Utils.addStrConstant(typeSpecBuilder, columnName);
        }
    }


}
