package com.huas.exception;
public class ErrorReturnResultException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;

    public ErrorReturnResultException() {

    }

    public ErrorReturnResultException(String message) {
        super(message);
    }
    public ErrorReturnResultException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
