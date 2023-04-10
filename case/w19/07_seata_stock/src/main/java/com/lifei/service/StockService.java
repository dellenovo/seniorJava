package com.lifei.service;

import com.lifei.domain.Stock;

public interface StockService {
    //新增库存
    boolean addStock(Stock stock);
    //根据名称查询库存
    Stock getStockByName(String name);
}
