package me.bucklb.simpleBootdemo.Logging;

import org.junit.Test;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;

/*
    Ought to be able to add stuff (and get stuff) from the MDC

    ?? Use MDC as a simple way of making values "global".  EG pick up userId from call, add to MDC and then its available throughout ??

    https://www.baeldung.com/mdc-in-log4j-2-logback
 */
public class MDCTest {

    @Test
    public void testMDC(){

        String key="headerName";
        String val="headerName";

        MDC.put(key,val);

        assertEquals(MDC.get(key),val);


    }



}
