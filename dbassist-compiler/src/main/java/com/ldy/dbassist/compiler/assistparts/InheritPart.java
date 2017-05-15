package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.annotations.Table;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by ldy on 2017/5/12.
 */

public class InheritPart extends com.ldy.dbassist.compiler.assistparts.base.ABAssistPart {

    public InheritPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        Table table = tabElement.getAnnotation(Table.class);
        try {
            Class superClass = table.inherit();
            if (!superClass.equals(Object.class)) {
                typeSpecBuilder.superclass(superClass);
            }
        } catch (MirroredTypeException e) {
            String className = e.getTypeMirror().toString();
            if (!className.equals("java.lang.Object")) {
                typeSpecBuilder.superclass(TypeVariableName.get(className));
            }
        }

    }

}
