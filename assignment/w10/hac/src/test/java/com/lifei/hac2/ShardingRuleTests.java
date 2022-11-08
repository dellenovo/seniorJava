package com.lifei.hac2;

import com.lifei.hac2.entity.Customer;
import com.lifei.hac2.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ShardingRuleTests {
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void testBatchAddCustomers() {
        int batch = 10;

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < batch ; i++) {
            Customer c = new Customer();
            c.setName("c" + i);
            long li = (long) i;
            c.setId(li);
            c.setMobile(li);
            customers.add(c);

            customerMapper.insert(c);
        }
    }

}
