package com.changgou.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/25/15:17
 * @ Description: 规格实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_spec")
public class Spec {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//id
    private String name;//规格名称
    private String options;//规格选项
    private Integer seq;//规格排序
    private Integer templateId;//模板id
}
