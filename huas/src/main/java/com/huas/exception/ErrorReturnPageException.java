package com.huas.exception;

public class ErrorReturnPageException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;


    public ErrorReturnPageException() {
    }

    public ErrorReturnPageException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ErrorReturnPageException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
