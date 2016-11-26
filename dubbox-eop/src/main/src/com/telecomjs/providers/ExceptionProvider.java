package com.telecomjs.providers;

import com.telecomjs.vo.EOPResponseHeader;
import com.telecomjs.vo.EOPResponseRoot;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by zark on 16/11/17.
 */
@Provider
public class ExceptionProvider implements ExceptionMapper<Exception> {
    Logger logger = Logger.getLogger(ExceptionProvider.class);


    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();
        logger.debug("ExceptionProvider triggered ");
        return Response.status(404).
                entity(new EOPResponseRoot().err(e.getMessage())).
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
    }
}