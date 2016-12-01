package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopServiceTimeoutException extends EopAppException {
    public EopServiceTimeoutException(Throwable cause) {
        super(EopExceptionText.SERVICE_CALL_TIMEOUT, cause);
    }
    public EopServiceTimeoutException(){this(new RuntimeException());}

    @Override
    public  String getText() {
        return  EopExceptionText.SERVICE_CALL_TIMEOUT;
    }

}
