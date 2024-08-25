package com.changgou.goods.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/25/10:17
 * @ Description: 分类、商品中间实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_category_brand")
public class CategoryBrand implements Serializable {
    private Integer categoryId;//分类id
    private Integer brandId;//品牌id
}
