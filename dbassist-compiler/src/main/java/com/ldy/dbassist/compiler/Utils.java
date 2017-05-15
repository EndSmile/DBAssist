package com.ldy.dbassist.compiler;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

/**
 * Created by ldy on 2017/5/11.
 */

public class Utils {

    public static String constantTransform(String string) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(string);
        String result = string;
        if (matcher.find()) {
            String group = matcher.group();
            result = string.replace(group, "_" + group);
        }
        return result.toUpperCase();
    }

    public static void addStrConstant(TypeSpec.Builder result, String constantValue) {
        addStrConstant(result, Utils.constantTransform(constantValue), constantValue);
    }

    public static void addStrConstant(TypeSpec.Builder result, String name, String value) {
        FieldSpec.Builder builder = FieldSpec.builder(TypeVariableName.get("String"),
                name
                , Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", value);
        result.addField(builder.build());
    }

    public static void addStaticStrVar(TypeSpec.Builder result, String name, String value) {
        FieldSpec.Builder builder = FieldSpec.builder(TypeVariableName.get("String"),
                name
                , Modifier.PUBLIC)
                .initializer("$S", value);
        result.addField(builder.build());
    }

    public static void error(Messager messager, Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    public static boolean empty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * <p>Uncapitalizes a String changing the first letter to title case as
     * per {@link Character#toLowerCase(char)}. No other letters are changed.</p>
     *
     * <p>For a word based algorithm,
     * A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.uncapitalize(null)  = null
     * StringUtils.uncapitalize("")    = ""
     * StringUtils.uncapitalize("Cat") = "cat"
     * StringUtils.uncapitalize("CAT") = "cAT"
     * </pre>
     *
     * @param str the String to uncapitalize, may be null
     * @return the uncapitalized String, {@code null} if null String input
     * @since 2.0
     */
    public static String uncapitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        if (Character.isLowerCase(firstChar)) {
            // already uncapitalized
            return str;
        }

        return new StringBuilder(strLen)
                .append(Character.toLowerCase(firstChar))
                .append(str.substring(1))
                .toString();
    }
}
