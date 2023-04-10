package com.lifei.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_brand")
@Data
public class Brand {
    @Id
    private Integer id;//品牌id
    private String name;//品牌名称
    private String image;//品牌图片地址
    private String letter;//品牌的首字母
    private Integer seq;//排序
}
