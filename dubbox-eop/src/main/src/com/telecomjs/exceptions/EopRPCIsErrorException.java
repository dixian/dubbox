package com.telecomjs.exceptions;

/**
 * Created by zark on 16/11/26.
 */
public class EopRPCIsErrorException extends EopAppException {
    public EopRPCIsErrorException(Throwable cause) {
        super(EopExceptionText.RPC_IS_ERROR, cause);
    }
    public EopRPCIsErrorException(){
        this(new RuntimeException());
    }

    @Override
    public  String getText() {
        return  EopExceptionText.RPC_IS_ERROR;
    }

}
