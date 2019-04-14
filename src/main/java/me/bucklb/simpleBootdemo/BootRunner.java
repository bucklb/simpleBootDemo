package me.bucklb.simpleBootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootRunner implements CommandLineRunner{

    Logger logger = LoggerFactory.getLogger(BootRunner.class);

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Runner is running ...");
        logger.info("BootRunner active");
    }
}
