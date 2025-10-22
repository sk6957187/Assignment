package io.mountblue.BlogApplication.exception;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String mesg) {
        super(mesg);
    }
}