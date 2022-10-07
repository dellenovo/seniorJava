package org.lifei.rpc.consumer.impl;

import org.lifei.rpc.consumer.SkuService;

public class SkuServiceImpl implements SkuService {
    @Override
    public String findByName(String name) {
        return "sku{}:" + name;
    }
}
