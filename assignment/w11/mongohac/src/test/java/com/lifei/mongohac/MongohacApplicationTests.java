package com.lifei.mongohac;

import com.lifei.mongohac.dao.EmployeeRepository;
import com.lifei.mongohac.entity.Employee;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class MongohacApplicationTests {
	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void add() {
		Employee employee = Employee.builder()
				.id("11").firstName("liu").lastName("hero").empId(1).salary(10200).build();
		employeeRepository.save(employee);
	}

	@Test
	public void findAll() {
		List<Employee> employees = employeeRepository.findAll();
		employees.forEach(System.out::println);
	}
}
