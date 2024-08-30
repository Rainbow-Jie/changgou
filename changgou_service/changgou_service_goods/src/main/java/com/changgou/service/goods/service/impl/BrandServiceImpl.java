package com.changgou.service.goods.service.impl;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.CategoryBrand;
import com.changgou.service.goods.dao.BrandMapper;

import com.changgou.service.goods.dao.CategoryBrandMapper;
import com.changgou.service.goods.dao.CategoryMapper;
import com.changgou.service.goods.service.IBrandService;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper,Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 品牌列表查询
     *
     * @return
     */
    @Override
    public List<Brand> findList() {

        List<Brand> brandList = brandMapper.selectList(null);
        return brandList;
    }

    /**
     * 根据id查询品牌信息
     *
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        Brand brand = brandMapper.selectById(id);
        return brand;
    }

    /**
     * 品牌新增
     *
     * @param brand
     */
    @Override
    @Transactional
    public void add(Brand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 品牌修改
     *
     * @param brand
     */
    @Override
    @Transactional
    public void update(Brand brand) {
        brandMapper.updateById(brand);
    }

    /**
     * 根据id删除品牌信息
     *
     * @param id
     */
    @Override
    @Transactional
    public void delById(Integer id) {
        brandMapper.deleteById(id);
    }

    /**
     * 品牌列表条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Brand> list(Map<String, Object> searchMap) {
//        Example example = new Example(Brand.class);
//        //封装查询条件
//        Example.Criteria criteria = example.createCriteria();
//        if (searchMap != null) {
//            //品牌名称(模糊) like  %
//            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
//                criteria.andLike("name", "%" + searchMap.get("name") + "%");
//            }
//            //按照品牌首字母进行查询(精确)
//            if (searchMap.get("letter") != null && !"".equals(searchMap.get("letter"))) {
//                criteria.andEqualTo("letter", searchMap.get("letter"));
//            }
//        }
//        List<Brand> brandList = brandMapper.selectByExample(example);
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<Brand>();
        wrapper.like(searchMap.get("name") != null && !"".equals(searchMap.get("name")),Brand::getName,searchMap.get("name"))
                .eq(searchMap.get("letter") != null && !"".equals(searchMap.get("letter")),Brand::getName,searchMap.get("letter"));
        List<Brand> brandList = brandMapper.selectList(wrapper);
        return brandList;
    }

    @Override
    public Page<Brand> findPage(int page, int size) {
//        PageHelper.startPage(page, size);
//        Page<Brand> page1 = (Page<Brand>) brandMapper.selectAll();
//        return page1;
        Page<Brand> brandPage = new Page<>(page, size);
        return brandMapper.selectPage(brandPage, null);
    }

    @Override
    public Page<Brand> findPage(Map<String, Object> searchMap, int page, int size) {
        //设置分页
//        PageHelper.startPage(page, size);

        //设置查询条件
//        Example example = new Example(Brand.class);
//        Example.Criteria criteria = example.createCriteria();
//        if (searchMap != null) {
//            //设置品牌名称模糊查询
//            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
//                criteria.andLike("name", "%" + searchMap.get("name") + "%");
//            }
//            //设置品牌首字母的精确查询
//            if (searchMap.get("letter") != null && !"".equals(searchMap.get("letter"))) {
//                criteria.andEqualTo("letter", searchMap.get("letter"));
//            }
//        }
//        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
//        return pageInfo;
        Page<Brand> brandPage = new Page<>(page, size);
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<Brand>();
        wrapper.like(searchMap.get("name") != null && !"".equals(searchMap.get("name")),Brand::getName,searchMap.get("name"))
                .eq(searchMap.get("letter") != null && !"".equals(searchMap.get("letter")),Brand::getName,searchMap.get("letter"));
        return brandMapper.selectPage(brandPage,wrapper);
    }


    public List<Map> findListByCategoryName(String categoryName){
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getName, categoryName));
        if(ObjectUtil.isEmpty(category)){
            return ListUtil.empty();
        }
        int categoryId = category.getId();
        List<CategoryBrand> categoryBrands = categoryBrandMapper.selectList(new LambdaQueryWrapper<CategoryBrand>().eq(CategoryBrand::getCategoryId, categoryId));
        if(ObjectUtil.isEmpty(categoryBrands)){
            return ListUtil.empty();
        }
        List<Integer> brandIds = categoryBrands.stream()
                .map(CategoryBrand::getBrandId)
                .collect(Collectors.toList());
        List<Brand> brandList = brandMapper.selectBatchIds(brandIds);
        List<Map> result = brandList.stream()
                .map(brand -> Map.of("name",brand.getName(),
                        "image",brand.getImage()))
                .collect(Collectors.toList());
        return result;
    }



}
