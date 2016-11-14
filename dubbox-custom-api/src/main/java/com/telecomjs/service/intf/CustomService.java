package com.telecomjs.service.intf;

import com.telecomjs.util.ResultMapper;
import com.telecomjs.vo.CustomerInfo;

import java.util.List;

/**
 * Created by zark on 16/11/7.
 */
public interface CustomService {
    public CustomerInfo getCustom(String customId);
}
