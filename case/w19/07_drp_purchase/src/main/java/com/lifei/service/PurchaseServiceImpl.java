package com.lifei.service;

import com.lifei.domain.Purchase;
import com.lifei.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;
    @Override
    public boolean addPurchase(Purchase purchase) {
        Purchase p = purchaseRepository.findByName(purchase.getName());
        if (p == null) {
            purchaseRepository.save(purchase);
            return true;
        } else {
            p.setCount(p.getCount() + purchase.getCount());
            purchaseRepository.save(p);
        }
        return true;
    }

    @Override
    public Purchase getPurchaseByName(String name) {
        return purchaseRepository.findByName(name);
    }
}
