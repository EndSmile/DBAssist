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

public class FiledPart extends com.ldy.dbassist.compiler.assistparts.base.ABAssistPart {

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
            if (!isColumn(childElement)) {
                continue;
            }
            Utils.addStrConstant(typeSpecBuilder, getColumnName(childElement));
        }
    }


}
