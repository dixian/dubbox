package com.telecomjs.exceptions;

import com.telecomjs.beans.EopAppOnlineBean;

/**
 * Created by zark on 16/11/26.
 */
public class EopTokenIsInvalidException extends EopAppException {
    public EopTokenIsInvalidException(Throwable cause) {
        super(EopExceptionText.TOKEN_IS_INVALID, cause);
    }

    public EopTokenIsInvalidException(){this(new RuntimeException());}
    @Override
    public  String getText() {
        return  EopExceptionText.TOKEN_IS_INVALID;
    }

}
