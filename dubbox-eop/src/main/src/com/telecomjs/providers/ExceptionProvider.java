package com.telecomjs.providers;

import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by zark on 16/11/17.
 */
@Provider
public class ExceptionProvider implements ExceptionMapper {
    Logger logger = Logger.getLogger(ExceptionProvider.class);

    @Override
    public Response toResponse(Throwable throwable) {
        logger.debug(throwable.getMessage());
        return Response.status(404).
                entity(throwable.getMessage()).
                type("text/plain").
                build();
    }
}