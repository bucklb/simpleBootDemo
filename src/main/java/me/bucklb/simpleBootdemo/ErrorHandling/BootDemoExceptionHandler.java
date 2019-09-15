package me.bucklb.simpleBootdemo.ErrorHandling;

import org.omg.SendingContext.RunTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/*
    Exercising the approach to getting something useful back in the event that a response is not a 200

    Seems to work as:

    Get something (in this case the pang endpoint) to raise an exception with a code and message
        ExceptionHandler then uses the exception to return a response message with a status
            RestTemplate directs this to an ErrorHandler which extracts the details from httpResponse and raises an exception
                and repeat exception->error message->error handler->exception ...

    The bit that is uncertain is the mechanics of getting the details from the httpClientResponse

    Effectively a chain of stuff
    exception -> exceptionHandler -> errorMessage (in httpClientResponse) -> template's errorHandler -> exception


 */


@RestControllerAdvice
public class BootDemoExceptionHandler {

    // Ideally we'd have something akin to BootDemoHttpRTE?
    // We defined this exception to require a code and a message.  We should be safe to extract (an re use) them
    // Step III turn a BootDemoRTE in to a ErroMessage (with code)
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> processRunTimeException(RuntimeException ex) {
        // Realistic to get a message from an RTE. Les so with a code (unless we add via an extension to RTE)
        String msg = ex.getMessage();
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("403",msg), HttpStatus.NOT_FOUND );
    }



}
