package com.telecomjs.service.impl;

import com.telecomjs.beans.CustomerBean;
import com.telecomjs.entities.Customer;
import com.telecomjs.entities.Party;
import com.telecomjs.mapper.CustomerMapper;
import com.telecomjs.mapper.PartyMapper;
import com.telecomjs.vo.CustomerInfo;
import com.telecomjs.service.intf.CustomService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zark on 16/11/7.
 */
@Service
public class CustomServiceImpl implements CustomService {
    Logger logger = Logger.getLogger(CustomService.class);
    @Autowired
    private PartyMapper partyMapper;
    @Autowired
    private CustomerMapper customerMapper;

    public CustomerInfo getCustom(String customId) {
        logger.debug("getCustom : "+String.valueOf(customId));
        CustomerInfo custom = null ;
        try {
            Customer cust =customerMapper.selectByPrimaryKey(customId);
            if (cust == null)
                return custom;
            CustomerBean bean = new CustomerBean();
            BeanUtils.copyProperties(cust,bean);
            custom = new CustomerInfo();
            custom.setCustomer(bean);
        }
        catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
        return custom;
    }


    public void throwNPE() throws NullPointerException {
        throw new NullPointerException();
    }
}
