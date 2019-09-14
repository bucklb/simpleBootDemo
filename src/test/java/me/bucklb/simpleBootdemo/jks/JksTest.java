package me.bucklb.simpleBootdemo.jks;

import java.security.KeyStore;
import java.lang.System;

public class JksTest {

    /*
        https://www.baeldung.com/java-keystore
    */

    // The steps
    public void testKeyStore()throws Exception{

        // Create
        // default is an Oracle proprietary format, but othe options are available
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());


//        System.setProperties("java.version");







    }



}
