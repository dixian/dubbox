package com.telecomjs.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.telecomjs.service.intf.ProductRestService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.util.ResultMapper;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zark on 16/11/10.
 */
@Controller
@RequestMapping(value = "/")
public class ProductController {
    Logger logger = Logger.getLogger(this.getClass());
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProductService productService;

    @RequestMapping("/pdinfo")
    public ModelAndView productInfo(){
        logger.warn("productInfo :" + "13347839809");
        ProductInfo result = productService.getProductByNbr("13347839809");
        //ProductInfo pd2=productService.getProductByNbr("13347839809");
        logger.debug("productRestService.getProductByNbr(\"13347839809\")");
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("info","info2");
        ModelAndView view = new ModelAndView("info",data);
        view.getModelMap().addAttribute("info","product");
        view.getModelMap().put("info","product");
        return view;
    }
}
