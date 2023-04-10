package com.lifei.goods.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult {
    private long total;
    private List result;
}
