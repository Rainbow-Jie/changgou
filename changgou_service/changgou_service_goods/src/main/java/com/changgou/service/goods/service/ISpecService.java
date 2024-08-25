package com.changgou.service.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changgou.goods.pojo.Spec;

import java.util.List;
import java.util.Map;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/25/15:16
 * @ Description:
 */
public interface ISpecService extends IService<Spec> {
    public List<Map> findListByCategoryName(String categoryName);
}
