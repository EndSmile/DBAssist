package com.ldy.dbassist.compiler.assistparts;

import com.ldy.dbassist.annotations.Index;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by ldy on 2017/5/12.
 */

public class CreateIndexSqlPart extends ABAssistPart {
    public CreateIndexSqlPart(Messager messager) {
        super(messager);
    }

    @Override
    public void addPart(TypeSpec.Builder typeSpecBuilder,TypeElement tabElement) {
        //// TODO: 2017/5/12 完成创建index sql语句
        StringBuilder indexBuilder = null;
        StringBuilder uniqueIndexBuilder = null;

        List<? extends Element> enclosedElements = tabElement.getEnclosedElements();
        for (int i = 0, length = enclosedElements.size(); i < length; i++) {
            Element childElement = enclosedElements.get(i);
            if (!isColumn(childElement)) {
                continue;
            }

            String columnName = getColumnName(childElement);

            Index index = childElement.getAnnotation(Index.class);

           if (index != null) {
                if (index.unique()) {
                    //unique index
                    if (uniqueIndexBuilder == null) {
                        uniqueIndexBuilder = new StringBuilder("UNIQUE INDEX(");
                    } else {
                        uniqueIndexBuilder.append(",");
                    }
                    uniqueIndexBuilder.append(columnName);
                } else {
                    //index
                    if (indexBuilder == null) {
                        indexBuilder = new StringBuilder("INDEX(");
                    } else {
                        indexBuilder.append(",");
                    }
                    indexBuilder.append(columnName);
                }

            }
        }

    }
}
