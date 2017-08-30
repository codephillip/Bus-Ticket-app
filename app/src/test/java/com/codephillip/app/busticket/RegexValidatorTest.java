package com.codephillip.app.busticket;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


import static org.junit.Assert.*;

/**
 * Created by codephillip on 23/08/17.
 */

public class RegexValidatorTest {

    @Test
    public void test_validators() throws Exception {
        assertEquals(5, 2 + 3);
        assertTrue(Utils.validateData("0756878567", Utils.PHONE_PATTERN));
        assertTrue(Utils.validateData("0786878567", Utils.PHONE_PATTERN));
        assertTrue(Utils.validateData("0776878567", Utils.PHONE_PATTERN));
        assertTrue(Utils.validateData("0706878567", Utils.PHONE_PATTERN));
        assertTrue(Utils.validateData("pas0*YhuUj*#e*", Utils.PASSWORD_PATTERN));
        assertTrue(Utils.validateData("1234", Utils.MM_CODE_PATTERN));
        assertTrue(Utils.validateData("6775", Utils.MM_CODE_PATTERN));
    }
}
