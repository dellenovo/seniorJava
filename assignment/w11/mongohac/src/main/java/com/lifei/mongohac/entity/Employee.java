package com.lifei.mongohac.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("employee")
public class Employee {
    @Id
    private String id;
    private int empId;
    private String firstName;
    private String lastName;
    private float salary;
}
