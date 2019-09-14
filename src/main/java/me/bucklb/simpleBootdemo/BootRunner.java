package me.bucklb.simpleBootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.time.LocalTime.now;

@Component
@EnableScheduling   // so I can toy with scheduling
public class BootRunner implements CommandLineRunner{

    Logger logger = LoggerFactory.getLogger(BootRunner.class);

    // When first job stops, after fixed delay from the stop it doesit ago
//    @Scheduled(fixedDelay = 4000)
    public void doFixedDelay(){
        System.out.println("fixed delay at " + now());
    }

    // Starts the task at a fixed interval, whether last iteration finished or not
//    @Scheduled(fixedRate = 5000)
    public void doFixedRate(){
        System.out.println("fixed rate at " + now());
    }

    // Can also build in CRON using @Scheduled(cron = "....")


    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Runner is running ...");
        logger.info("BootRunner active");



    }
}
