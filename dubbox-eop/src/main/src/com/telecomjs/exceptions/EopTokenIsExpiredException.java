package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopTokenIsExpiredException extends EopAppException {
    public EopTokenIsExpiredException(Throwable cause) {
        super(EopExceptionText.TOKEN_IS_EXPIRED, cause);
    }

    public EopTokenIsExpiredException(){this(new RuntimeException());}

    @Override
    public  String getText() {
        return  EopExceptionText.TOKEN_IS_EXPIRED;
    }

}
