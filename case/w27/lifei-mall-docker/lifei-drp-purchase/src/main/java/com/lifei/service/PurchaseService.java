package com.lifei.service;

import com.lifei.bean.Purchase;

public interface PurchaseService {
    //新增库存
    boolean addPurchase(Purchase purchase);
    //根据名称查询库存
    Purchase getPurchaseByName(String name);
}
