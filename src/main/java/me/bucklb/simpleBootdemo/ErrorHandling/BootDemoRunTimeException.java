package me.bucklb.simpleBootdemo.ErrorHandling;


public class BootDemoRunTimeException extends RuntimeException {

    String code;
    String message;

    public BootDemoRunTimeException(String code, String message) {
        super();
        this.code=code;
        this.message=message;
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
