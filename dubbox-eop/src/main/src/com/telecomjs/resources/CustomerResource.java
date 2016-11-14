package com.telecomjs.resources;

import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.CustomerInfo;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by zark on 16/11/14.
 */
@Component
@Path("/cust")
public class CustomerResource {
    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private CustomService customService;
    @Autowired
    private ProductService productService;

    @Path("{id : \\d+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getCustomer(@PathParam("id")String partyId){
        logger.warn("getCustomer : "+partyId);

        try {

            CustomerInfo pi = customService.getCustom(partyId);
            return Response.ok(pi).build();

        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }

    }

    @Path("/byaccnbr/{accNbr : \\d+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getCustomerByAccNbr(@PathParam("accNbr")String accNbr){
        logger.warn("getCustomerByAccNbr : "+accNbr);

        try {

            ProductInfo pi = productService.getProductByNbr(accNbr);

            CustomerInfo result = customService.getCustom(pi.getProdInst().getOwnerCustId());
            List<ProdInstBean> list = productService.queryProductByPartyId(result.getCustomer().getPartyId());
            if (list != null){
                result.setProdlist(list);
            }
            return Response.ok(result).build();

        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }

    }
}
