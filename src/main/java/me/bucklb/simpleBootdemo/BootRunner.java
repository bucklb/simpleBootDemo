package me.bucklb.simpleBootdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootRunner implements CommandLineRunner{

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Runner is running ...");
    }
}
