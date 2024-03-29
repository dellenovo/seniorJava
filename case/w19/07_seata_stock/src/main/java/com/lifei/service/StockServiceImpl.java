package com.lifei.service;

import com.lifei.domain.Stock;
import com.lifei.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{
    @Autowired
    StockRepository stockRepository;
    @Override
    public boolean addStock(Stock stock) {
        Stock s = stockRepository.findByName(stock.getName());
        if (s == null) {
            stockRepository.save(stock);
            return true;
        } else {
            s.setTotal(s.getTotal() + stock.getTotal());
            stockRepository.save(s);
        }
        return true;
    }

    @Override
    public Stock getStockByName(String name) {
        return stockRepository.findByName(name);
    }
}
