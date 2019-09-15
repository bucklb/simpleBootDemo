package me.bucklb.simpleBootdemo.ErrorHandling;

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







public class ErrorMessage {

    String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String code, String message) {
        this.code=code;
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
//        String s = "{\"code\":\""+code+"\",\"message\":\""+message+"\"}";
        String s = "{\"message\":\""+message+"\",\"code\":\""+code+"\"}";
        return s;
    }
}
