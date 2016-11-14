package com.telecomjs.service.intf;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.util.ResultMapper;
import com.telecomjs.vo.ProductInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zark on 16/11/9.
 */

public interface ProductRestService {

    public ProductInfo getProductByNbr(String accNbr);
    public List<ProdInstBean> queryProductByPartyId(String partyId);
}
