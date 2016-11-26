package com.telecomjs.exceptions;

import org.jboss.resteasy.spi.ApplicationException;

/**
 * Created by zark on 16/11/26.
 */
public class EopAppException extends RuntimeException {
    private String  exceptionText;

    public EopAppException(String message, Throwable cause ) {
        super(message, cause);
        this.exceptionText = message;
    }

    public String getExceptionText() {
        return exceptionText;
    }

    public void setExceptionText(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    public String getText(){return getExceptionText();}
}
