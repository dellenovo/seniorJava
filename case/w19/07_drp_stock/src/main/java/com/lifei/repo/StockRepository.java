package com.lifei.repo;

import com.lifei.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    //根据名称查询库存
    Stock findByName(String name);
}
