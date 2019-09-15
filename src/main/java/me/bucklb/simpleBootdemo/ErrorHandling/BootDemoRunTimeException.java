package me.bucklb.simpleBootdemo.ErrorHandling;


public class BootDemoRunTimeException extends RuntimeException {

    String code;
    String message;

    // For this exercise really just want to pass the response code & message up the system, so need code and message
    // CODE & MESSAGE explicitly
    public BootDemoRunTimeException(String code, String message) {
        super();
        this.code=code;
        this.message=message;
    }

    // VIA the ErrorMessage object
    public BootDemoRunTimeException(ErrorMessage eMsg) {
        super();
        if( eMsg==null) {
            this.code="500";
            this.message="null message provided";
        } else {
            this.code=eMsg.code;
            this.message=eMsg.message;
        }
    }


    public String getCode() {        return code;    }
    public void setCode(String code) {        this.code = code;    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
