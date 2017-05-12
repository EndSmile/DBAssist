package com.ldy.dbassist.compiler.assistparts;

import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;

/**
 * Created by ldy on 2017/5/12.
 */

public interface IAddPart {
    void addPart(TypeSpec.Builder typeSpecBuilder, TypeElement tabElement);
}
