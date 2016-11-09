package com.telecomjs.service.impl;

import com.telecomjs.entities.Party;
import com.telecomjs.mapper.PartyMapper;
import com.telecomjs.vo.CustomerInfo;
import com.telecomjs.service.intf.CustomService;
import org.apache.log4j.Logger;
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

    public CustomerInfo getCustom(String customId) {
        logger.debug("getCustom : "+String.valueOf(customId));
        try {
            Party p = partyMapper.selectByPrimaryKey(customId);
            CustomerInfo custom = new CustomerInfo();
            custom.setId(String.valueOf(p.getPartyId()));
            custom.setName(p.getPartyName());
            custom.setCode(p.getEnglishName());
            return custom;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerInfo getCustomByAccNbr(String number) {
        return null;
    }

    @Override
    public List<CustomerInfo> getAccNbrAll(String customId) {
        return null;
    }

    public void throwNPE() throws NullPointerException {
        throw new NullPointerException();
    }
}
