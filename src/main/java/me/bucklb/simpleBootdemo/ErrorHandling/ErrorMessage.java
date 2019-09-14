package me.bucklb.simpleBootdemo.ErrorHandling;

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
