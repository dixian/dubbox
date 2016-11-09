package com.telecomjs.service.intf;

import com.telecomjs.vo.CustomerInfo;

import java.util.List;

/**
 * Created by zark on 16/11/7.
 */
public interface CustomService {
    public CustomerInfo getCustom(String customId);
    public CustomerInfo getCustomByAccNbr(String number);
    public List<CustomerInfo> getAccNbrAll(String customId);
}
