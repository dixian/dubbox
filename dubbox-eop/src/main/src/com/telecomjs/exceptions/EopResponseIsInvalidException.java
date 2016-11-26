package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopResponseIsInvalidException extends EopAppException {
    public EopResponseIsInvalidException(Throwable cause) {
        super(EopExceptionText.ASYNC_RESPONSE_IS_INVALID, cause);
    }

    public EopResponseIsInvalidException(){this(new RuntimeException());}
    @Override
    public  String getText() {
        return  EopExceptionText.ASYNC_RESPONSE_IS_INVALID;
    }

}
