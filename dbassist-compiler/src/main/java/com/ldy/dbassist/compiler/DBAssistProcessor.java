package com.ldy.dbassist.compiler;

import com.google.auto.common.SuperficialValidation;
import com.ldy.dbassist.annotations.Table;
import com.ldy.dbassist.compiler.assistparts.ABAssistPart;
import com.ldy.dbassist.compiler.assistparts.FiledPart;
import com.ldy.dbassist.compiler.assistparts.CursorTransformPart;
import com.ldy.dbassist.compiler.assistparts.Transform2ContentValuesPart;
import com.ldy.dbassist.compiler.assistparts.CreateSqlPart;
import com.ldy.dbassist.compiler.assistparts.InheritPart;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by ldy on 2017/5/11.
 */

public class DBAssistProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
        messager = env.getMessager();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("process");

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Table.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();

            TypeSpec.Builder result = TypeSpec.classBuilder(ABAssistPart.getTabName((TypeElement) element) + "AssistDao")
                    .addModifiers(PUBLIC);
            try {
                new FiledPart(messager).addPart(result, (TypeElement) element);
                new CreateSqlPart(messager).addPart(result, (TypeElement) element);
                new Transform2ContentValuesPart(messager).addPart(result, (TypeElement) element);
                new InheritPart(messager).addPart(result, (TypeElement) element);
                new CursorTransformPart(messager).addPart(result, (TypeElement) element);

                System.out.println(result);
            }catch (Exception e){
                e.printStackTrace();
            }



            try {
                JavaFile.builder(packageName, result.build())
                        .addFileComment("Generated code from DBAssist. Do not modify!")
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }




    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(Table.class.getCanonicalName());
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
