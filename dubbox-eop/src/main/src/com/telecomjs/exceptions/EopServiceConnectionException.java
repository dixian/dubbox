package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopServiceConnectionException extends EopAppException {
    public EopServiceConnectionException(Throwable cause) {
        super(EopExceptionText.SERVICE_CONNECTION_IS_ERROR, cause);
    }
    public EopServiceConnectionException(){this(new RuntimeException());}

    @Override
    public  String getText() {
        return  EopExceptionText.SERVICE_CONNECTION_IS_ERROR;
    }

}
