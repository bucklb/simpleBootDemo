package me.bucklb.simpleBootdemo.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BootDemoExceptionHandler {

    // If we haven't been gifted an error list then create a rather basic one as best we can
    @ExceptionHandler(BootDemoRunTimeException.class)
    public ResponseEntity<ErrorMessage> processApplicationException(BootDemoRunTimeException ex) {

        System.out.println("Handling exception " + ex.getCode() + " : " + ex.getMessage());

        ErrorMessage errorResponse = new ErrorMessage(ex.getCode(), ex.getMessage());
        String stringCode = ex.getCode();
        int intCode = Integer.parseInt(stringCode);
        HttpStatus httpStatus = HttpStatus.valueOf(intCode);

        // Turn the contents of the exception back in to an httpResponse
        return new ResponseEntity<ErrorMessage>(errorResponse, httpStatus );
    }

}
