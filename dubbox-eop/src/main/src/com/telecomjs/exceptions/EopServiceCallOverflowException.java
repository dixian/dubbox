package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopServiceCallOverflowException extends EopAppException {
    public EopServiceCallOverflowException(Throwable cause) {
        super(EopExceptionText.SERVICE_CALL_OVERFLOW, cause);
    }
    public EopServiceCallOverflowException() {
        this(new RuntimeException());
    }

    @Override
    public  String getText() {
        return  EopExceptionText.SERVICE_CALL_OVERFLOW ;
    }

}
