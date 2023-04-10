package com.lifei.repo;

import com.lifei.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findByName(String name);
}
