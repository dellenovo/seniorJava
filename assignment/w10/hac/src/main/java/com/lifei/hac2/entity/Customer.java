package com.lifei.hac2.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("CUSTOMER")
public class Customer {
    private Long id;
    private String name;
}
