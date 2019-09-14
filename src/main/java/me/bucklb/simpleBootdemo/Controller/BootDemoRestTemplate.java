package me.bucklb.simpleBootdemo.Controller;

import me.bucklb.simpleBootdemo.ErrorHandling.BootDemoResponseErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/*
    Possibly overkill to have it as a class in its own right, but hopefully keeps stuff clear

    NOTE - extra options like passing stuff in to it (via autowire)


 */
@Component
public class BootDemoRestTemplate extends RestTemplate
{

    // Don't need to have much more error handling if we treat anything other than 200 as unacceptable/error
    private static final List<HttpStatus> ACCEPTABLE_RESPONSE_CODES = Arrays.asList(HttpStatus.OK);//, HttpStatus.FORBIDDEN, HttpStatus.BAD_REQUEST);

    // From NISP stuff at work.  Allows option to ste up a specific
    public BootDemoRestTemplate(){
        super();
        this.setErrorHandler(new BootDemoResponseErrorHandler(ACCEPTABLE_RESPONSE_CODES));
    }

}
