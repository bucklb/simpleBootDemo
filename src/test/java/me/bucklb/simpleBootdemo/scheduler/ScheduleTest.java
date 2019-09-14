package me.bucklb.simpleBootdemo.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import static java.time.LocalTime.now;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableScheduling   // so I can toy with scheduling
public class ScheduleTest {

    @Test
    public void nodAndSmile(){

    }

    @Scheduled(fixedRate = 1000)
    public void doFixedRate(){
        System.out.println("fixed rate at " + now());
    }

    @Test
    public void fixedTest(){

    }





}
