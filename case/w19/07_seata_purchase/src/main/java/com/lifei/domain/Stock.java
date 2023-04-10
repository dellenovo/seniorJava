package com.lifei.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class Stock {
    private Integer id;
    private String name;//库存名称
    private Integer total;//资源数量
}
