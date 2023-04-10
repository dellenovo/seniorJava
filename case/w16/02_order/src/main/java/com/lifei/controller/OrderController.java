package com.lifei.controller;

import com.lifei.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> payment(@PathVariable("id")Integer id) {
        String url = "http://localhost:8081/payment/" + id;
        Payment payment = restTemplate.getForObject(url, Payment.class);
        return ResponseEntity.ok(payment);
    }

}
