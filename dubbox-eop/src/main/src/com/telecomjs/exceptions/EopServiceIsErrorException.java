package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopServiceIsErrorException extends EopAppException {
    public EopServiceIsErrorException(Throwable cause) {
        super(EopExceptionText.SERVICE_IS_ERROR, cause);
    }
    public EopServiceIsErrorException(){this(new RuntimeException());}

    @Override
    public  String getText() {
        return  EopExceptionText.SERVICE_IS_ERROR;
    }

}
