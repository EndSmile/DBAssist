package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.compiler.Utils;
import com.ldy.dbassist.compiler.assistparts.base.ABAssistPart;
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

public class ModelRandomPart extends ABAssistPart {


    public ModelRandomPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement) {
        String methodName = Utils.uncapitalize(tabElement.getSimpleName().toString())+"Random";

        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
                .addModifiers(PUBLIC, STATIC)
                .returns(TypeVariableName.get(tabElement.asType()));
        for (Element childElement:tabElement.getEnclosedElements()){
            if (!isField(childElement)){
                continue;
            }
            TypeMirror typeMirror = childElement.asType();
            switch (ColumnType.valueOf(typeMirror)){
                case BOOLEAN:
                    break;
                case INT:
                    break;
                case LONG:
                    break;
                case SHORT:
                    break;
                case FLOAT:
                    break;
                case DOUBLE:
                    break;
                case STRING:
                    break;
                case UNKNOWN:
            }
        }
    }
}
