package com.changgou.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/25/9:47
 * @ Description: 分类实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_category")
public class Category implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//分类id
    private String name;//分类名称
    private Integer goodsNum;
    private Character isShow;//是否显示
    private Character isMenu;//是否导航
    private Integer seq;//排序
    private Integer parentId;//上级id
    private Integer templateId;//模板id
}
