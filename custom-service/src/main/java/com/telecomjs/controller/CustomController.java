package com.telecomjs.controller;

import com.telecomjs.service.intf.CustomService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zark on 16/11/7.
 */
@Controller
public class CustomController {

    Logger logger = Logger.getLogger(CustomController.class);

    @Autowired
    private CustomService customService;

    @RequestMapping("/hello")
    public String index(){
        logger.debug("index : "+customService.getCustom("1").toString());
        return "hello";
    }
}
