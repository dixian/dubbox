package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopAuthorizationFailedException extends EopAppException {
    public EopAuthorizationFailedException(Throwable cause) {
        super(EopExceptionText.AUTHORIZATION_FAILED, cause);
    }

    public EopAuthorizationFailedException(){this(new RuntimeException());}

    @Override
    public  String getText() {
        return  EopExceptionText.AUTHORIZATION_FAILED;
    }

}
