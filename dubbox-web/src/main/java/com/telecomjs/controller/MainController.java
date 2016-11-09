package com.telecomjs.controller;

import com.alibaba.fastjson.JSON;
import com.telecomjs.service.intf.CustomRestService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.vo.CustomerInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zark on 16/11/7.
 */
@Controller
public class MainController {
    Logger log = Logger.getLogger(this.getClass());

    @SuppressWarnings("all")
    @Autowired
    private CustomService customService;
    @SuppressWarnings("all")
    @Autowired
    private CustomRestService customRestService;

    public void setCustomService(CustomService customService) {
        this.customService = customService;
    }

    public void setCustomRestService(CustomRestService customRestService) {
        this.customRestService = customRestService;
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/showcustom/{id}")
    public ModelAndView showCustomInfo(@PathVariable("id") String id) {
        log.debug("pathparam id :"+String.valueOf(id));
        log.debug("customservice is null ?" + String.valueOf(customService==null));
        try {
            CustomerInfo info = customService.getCustom(id);
            String message = JSON.toJSONString(info);
            log.warn(message);
            ModelAndView mav = new ModelAndView("show");
            mav.getModelMap().put("message", message);
            return mav;
        }
        catch (Exception e){
            e.printStackTrace();
            log.warn("showcustom : "+e.toString());
            ModelAndView mav = new ModelAndView("404");
            mav.getModelMap().put("message", e.toString());
            return mav;
        }
    }
    @RequestMapping("/showcustom")
    public String show404() {
        log.debug("pathparam : return to 404.jsp" );

        return "404";
    }
}
