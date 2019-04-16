package me.bucklb.simpleBootdemo.Logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// Want the logging config in play, so run as near fully as we can (pending working out how we set up the headers etc)
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    // Toy with logging to get sleuth stuff visible
    @Test
    public void testLogging() {

        logger.info("log message");

        System.out.println(MDC.get("traceId"));
        System.out.println(MDC.get("X-B3-TraceId"));



    }


}
