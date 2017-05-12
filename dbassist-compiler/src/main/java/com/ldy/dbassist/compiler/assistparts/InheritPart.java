package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.annotations.Table;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.lang.reflect.Type;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Created by ldy on 2017/5/12.
 */

public class InheritPart extends ABAssistPart {

    public InheritPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        Table table = tabElement.getAnnotation(Table.class);
        try {
            typeSpecBuilder.superclass(table.inherit());
        } catch (MirroredTypeException e) {
            String className = e.getTypeMirror().toString();
            typeSpecBuilder.superclass(TypeVariableName.get(className));
        }
    }

}
