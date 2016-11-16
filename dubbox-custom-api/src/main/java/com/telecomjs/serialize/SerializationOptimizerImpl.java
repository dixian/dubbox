/**
 * Copyright 1999-2014 dangdang.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.telecomjs.serialize;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.telecomjs.beans.*;
import com.telecomjs.util.ResultMapper;
import com.telecomjs.vo.CustomerInfo;
import com.telecomjs.vo.ProductInfo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(CustomerInfo.class);
        classes.add(ProductInfo.class);
        classes.add(ResultMapper.class);
        classes.add(CustomerBean.class);
        classes.add(OfferBean.class);
        classes.add(OfferInstBean.class);
        classes.add(PartyBean.class);
        classes.add(ProdInstBean.class);
        return classes;
    }
}
