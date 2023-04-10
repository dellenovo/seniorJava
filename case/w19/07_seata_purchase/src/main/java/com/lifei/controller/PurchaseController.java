package com.lifei.controller;

import com.lifei.domain.Purchase;
import com.lifei.domain.Stock;
import com.lifei.service.PurchaseService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private RestTemplate restTemplate;

    @GlobalTransactional
    @RequestMapping("/purchase/add/{deno}")
    public boolean addPurchaseHandle(@RequestBody Purchase purchase, @PathVariable("deno") int deno) {
        purchaseService.addPurchase(purchase);

        Stock stock = new Stock();
        stock.setName(purchase.getName());
        stock.setTotal(purchase.getCount());

        String url = "http://msc-stock/stock/add";
        Boolean result = restTemplate.postForObject(url, stock, Boolean.class);

        //手动异常
        int i = 3 / deno;
        if (result != null) {
            return result;
        }

        return false;
    }
}
