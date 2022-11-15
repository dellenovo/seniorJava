package com.lifei.hac2;

import com.lifei.hac2.entity.Customer;
import com.lifei.hac2.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class Hac2ApplicationTests {
	@Autowired
	private CustomerMapper customerMapper;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<Customer> customerList = customerMapper.selectList(null);
		assertEquals(2, customerList.size());
		customerList.forEach(System.out::println);
	}

	@Test
	public void testCreate() {
		Customer customer = new Customer();
		customer.setId(100L);
		customer.setName("Lifei");
		customerMapper.insert(customer);

		Customer cdb = customerMapper.selectById(customer.getId());
		assertEquals(customer.getId(), cdb.getId());

		customer.setName("Lifei Zhang");
		int affectedCount = customerMapper.updateById(customer);
		assertEquals(1, affectedCount);

		cdb = customerMapper.selectById(customer.getId());
		assertEquals(customer.getName(), cdb.getName());

		int deletedCount = customerMapper.deleteById(customer);
		assertEquals(1, deletedCount);

		cdb = customerMapper.selectById(customer.getId());
		assertNull(cdb);
	}

	@Test
	public void testMyCat2M1S() {
		customerMapper.delete(null);
		for (int i = 0; i < 2; i++) {
			Customer customer = new Customer();
			customer.setId((long)i);
			customer.setName("Lifei" + i);
			customerMapper.insert(customer);
		}

		List<Customer> customerList = customerMapper.selectList(null);
		assertEquals(2, customerList.size());
	}

}
