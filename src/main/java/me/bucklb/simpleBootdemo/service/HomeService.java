package me.bucklb.simpleBootdemo.service;

import me.bucklb.simpleBootdemo.ErrorHandling.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
@Service
public class HomeService {

    String BR="<br>";

    public String greeting() {
        String s = "There's no place like it!!\n\n"+BR+BR+
                "swagger: get      : swagger documentation\n"+BR+
                "info:    get      : swagger documentation\n"+BR;
        return s;
    }

    // Want to see how to get a response that is not httpSpecific, but certainly could become so
    public ErrorMessage getPangErrorResponse() {
        ErrorMessage errMsg=new ErrorMessage("401","Forbidden!");
        return errMsg;
    }





}
