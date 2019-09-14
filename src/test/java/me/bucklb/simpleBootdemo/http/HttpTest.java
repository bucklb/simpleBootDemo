package me.bucklb.simpleBootdemo.http;

import io.swagger.models.auth.In;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class HttpTest {

    //Pass around httpStatus without needing it to be so

    @Test
    public void testStatus() {
        // Allows possibility of passing what matters about an httpStatus without passing it as an HttpStatus
        HttpStatus s=HttpStatus.valueOf(404);
        System.out.println(s.toString());
        System.out.println(s.value());
        System.out.println(String.valueOf(s.value()));

    }

    @Test
    public void testStringStatus () {
        String sts = "403";
        System.out.println(Integer.parseInt(sts));
    }



}
