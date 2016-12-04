package com.telecomjs.service.rest;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.constants.DubboxConstants;
import com.telecomjs.service.intf.ProductRestService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.util.ResultMapper;
import com.telecomjs.util.ResultMapperUtil;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zark on 16/11/9.
 */
@Path("product")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
public class ProductRestServiceImpl implements ProductRestService {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }



    @Override
    @GET
    @Path("querypartyprods/{partyId : \\w+}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public List<ProdInstBean> queryProductByPartyId(@PathParam("partyId") String partyId) {
        return productService.queryProductByPartyId(partyId);
    }

    @Override
    @GET
    @Path("{accNbr : \\d+}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public ProductInfo getProductByNbr(@PathParam("accNbr") String accNbr) {
        logger.debug("getDubboProductByNbr :"+accNbr);
        return productService.getProductByNbr(accNbr);
    }



}
