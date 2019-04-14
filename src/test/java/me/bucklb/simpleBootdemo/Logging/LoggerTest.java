package me.bucklb.simpleBootdemo.Logging;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

    Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    // Toy with logging to get sleuth stuff visible
    @Test
    public void testLogging() {

        logger.info("log message");
    }


}
