package com.telecomjs.resources;

import com.telecomjs.service.intf.ProductRestService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zark on 16/11/10.
 */
@Component
@Path("/prod")
public class ProductResource {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductRestService productRestService;
    @Autowired
    private ProductService productService;

    @Path("/test")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public Response findUsers() {
        logger.debug("findUsers: " );
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("world");
        GenericEntity entity = new GenericEntity<List<String>>(list) {};

        return Response.ok( entity).build();
    }

    @Path("/get/{accNbr : \\d+}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getProduct(@PathParam("accNbr")String accNbr){
        logger.warn("getProduct : "+accNbr);

        try {
            ProductInfo pd = productRestService.getProductByNbr(accNbr);
            return Response.ok(pd).build();

        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }

    }
}
