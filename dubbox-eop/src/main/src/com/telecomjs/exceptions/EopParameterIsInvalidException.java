package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopParameterIsInvalidException extends EopAppException {

    public EopParameterIsInvalidException( Throwable cause) {
        super(EopExceptionText.PARAMETER_IS_INVALID, cause);
    }
    public EopParameterIsInvalidException() {
        this(new RuntimeException());
    }
    public EopParameterIsInvalidException(String message){
        super(EopExceptionText.PARAMETER_IS_INVALID+message,new RuntimeException());
    }

}
