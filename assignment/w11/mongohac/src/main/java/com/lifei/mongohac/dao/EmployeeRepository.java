package com.lifei.mongohac.dao;

import com.lifei.mongohac.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
