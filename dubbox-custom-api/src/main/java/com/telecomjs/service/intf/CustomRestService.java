package com.telecomjs.service.intf;

import com.telecomjs.util.ResultMapper;
import com.telecomjs.vo.CustomerInfo;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * Created by zark on 16/11/8.
 */
public interface CustomRestService {
    public ResultMapper getCustom(@NotNull(message="User ID must be not null.") String customId);
    public CustomerInfo getCustomByAccNbr(@NotNull(message="Number must be not null.")String number);
    public List<CustomerInfo> getAccNbrAll(int customId);
}
