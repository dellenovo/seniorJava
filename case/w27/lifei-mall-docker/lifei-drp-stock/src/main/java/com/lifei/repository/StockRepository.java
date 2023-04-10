package com.lifei.repository;

import com.lifei.bean.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @Author yaxiongliu
 **/
public interface StockRepository extends JpaRepository<Stock,Integer> {
    //根据名称查询库存
    Stock findByName(String name);
}
