package com.ldy.dbassist.compiler;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ldy on 2017/5/11.
 */
public class UtilsTest {
    @Test
    public void constantTransform() throws Exception {
        assertEquals("PERSON", Utils.constantTransform("person"));
        assertEquals("PERSON_NAME", Utils.constantTransform("personName"));
        assertEquals("PERSON_NAME_NUMBER", Utils.constantTransform("personNameNumber"));
    }

}