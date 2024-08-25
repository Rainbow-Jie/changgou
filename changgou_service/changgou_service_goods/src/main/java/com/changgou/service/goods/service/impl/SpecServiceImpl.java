package com.changgou.service.goods.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Spec;
import com.changgou.service.goods.dao.BrandMapper;
import com.changgou.service.goods.dao.CategoryMapper;
import com.changgou.service.goods.dao.SpecMapper;
import com.changgou.service.goods.service.IBrandService;
import com.changgou.service.goods.service.ISpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/25/15:21
 * @ Description:
 */
@Service
public class SpecServiceImpl extends ServiceImpl<SpecMapper, Spec> implements ISpecService {
    @Autowired
    private SpecMapper specMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Map> findListByCategoryName(String categoryName) {
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getName, categoryName));
        if (ObjectUtil.isEmpty(category)) {
            return ListUtil.empty();
        }
        int templateId = category.getTemplateId();

        List<Spec> specList = specMapper.selectList(new LambdaQueryWrapper<Spec>().eq(Spec::getTemplateId, templateId));

        List<Map> resList = specList.stream()
                .map(spec -> Map.of("name", spec.getName(),
                        "options", spec.getOptions().split(",")))
                .collect(Collectors.toList());
        return resList;
    }
}
