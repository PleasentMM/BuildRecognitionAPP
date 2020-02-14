package com.imurluck.net;

public class ApiException extends Exception {

    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }


}
