package com.changgou.service.goods.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changgou.common.util.IdWorker;
import com.changgou.goods.pojo.*;
import com.changgou.service.goods.controller.SkuMapper;
import com.changgou.service.goods.dao.BrandMapper;
import com.changgou.service.goods.dao.CategoryBrandMapper;
import com.changgou.service.goods.dao.CategoryMapper;
import com.changgou.service.goods.dao.SpuMapper;
import com.changgou.service.goods.service.ISpuService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/28/21:37
 * @ Description:
 */
@Service
@Slf4j
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ISpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void add(Goods goods) {
        // 获得spu
        Spu spu = goods.getSpu();
        Brand brand = brandMapper.selectById(spu.getBrandId());
        Category category = categoryMapper.selectById(spu.getCategory3Id());

        addSku(goods, spu, category, brand);
    }

    private void saveSkuList(Goods goods) {
        //获取spu对象
        Spu spu = goods.getSpu();
        //获取品牌对象
        Brand brand = brandMapper.selectById(spu.getBrandId());
        //获取分类对象
        Category category = categoryMapper.selectById(spu.getCategory3Id());
        /**
         * 添加分类与品牌之间的关联
         */
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(spu.getBrandId());
        categoryBrand.setCategoryId(spu.getCategory3Id());
        int count = categoryBrandMapper.selectCount(new LambdaQueryWrapper<CategoryBrand>()
                .eq(CategoryBrand::getCategoryId, spu.getCategory3Id())
                .eq(CategoryBrand::getBrandId, spu.getBrandId()));
        //判断是否有这个品牌和分类的关系数据
        if (count == 0) {
            //如果没有关系数据则添加品牌和分类关系数据
            categoryBrandMapper.insert(categoryBrand);
        }
        addSku(goods, spu, category, brand);
    }

    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    @Override
    public Goods findGoodsById(String id){
        //查询spu
        Spu spu = spuMapper.selectById(id);
        //查询SKU 列表
        List<Sku> skuList = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getSpuId,id));

        //封装，返回
        Goods goods=new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    @Override
    public void update(Goods goods ) {
        //取出spu部分
        Spu spu = goods.getSpu();
        spuMapper.updateById(spu);
        //删除原sku列表
        skuMapper.delete(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getSpuId,spu.getId()));

        saveSkuList(goods);//保存sku列表
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        Spu spu = spuMapper.selectById(id);
        //检查是否下架的商品
        if(!spu.getIsMarketable().equals("0")){
            throw new RuntimeException("必须先下架再删除！");
        }
        spu.setIsDelete("1");//删除
        spu.setStatus("0");//未审核
        spuMapper.updateById(spu);
    }

    @Transactional
    @Override
    public void audit(String id) {
        //查询spu对象
        Spu spu = spuMapper.selectById(id);
        if (spu == null){
            throw new RuntimeException("当前商品不存在");
        }
        //判断当前spu是否处于删除状态
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("当前商品处于删除状态");
        }
        //不处于删除状态,修改审核状态为1,上下架状态为1
        spu.setStatus("1");
        spu.setIsMarketable("1");
        //执行修改操作
        spuMapper.updateById(spu);
    }

    @Transactional
    @Override
    public void pull(String id) {
        //查询spu
        Spu spu = spuMapper.selectById(id);
        if (spu == null){
            throw new RuntimeException("当前商品不存在");
        }
        //判断当前商品是否处于删除状态
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("当前商品处于删除状态");
        }
        //商品处于未删除状态的话,则修改上下架状态为已下架(0)
        spu.setIsMarketable("0");
        spuMapper.updateById(spu);
    }

    /**
     * 上架商品
     * @param id
     */
    @Override
    public void put(String id) {
        Spu spu = spuMapper.selectById(id);
        if(!spu.getStatus().equals("1")){
            throw new RuntimeException("未通过审核的商品不能上架！");
        }
        spu.setIsMarketable("1");//上架状态
        spuMapper.updateById(spu);
    }

    /**
     * 恢复数据
     * @param id
     */
    @Override
    public void restore(String id) {
        Spu spu = spuMapper.selectById(id);
        //检查是否删除的商品
        if(!spu.getIsDelete().equals("1")){
            throw new RuntimeException("此商品未删除！");
        }
        spu.setIsDelete("0");//未删除
        spu.setStatus("0");//未审核
        spuMapper.updateById(spu);
    }

    @Override
    public void realDelete(String id) {
        Spu spu = spuMapper.selectById(id);
        //检查是否删除的商品
        if(!spu.getIsDelete().equals("1")){
            throw new RuntimeException("此商品未删除！");
        }
        spuMapper.deleteById(id);

    }

    private void addSku(Goods goods, Spu spu, Category category, Brand brand) {
        //获取sku集合对象
        List<Sku> skuList = goods.getSkuList();
        if (skuList != null) {
            for (Sku sku : skuList) {
                //设置sku主键ID
                sku.setId(String.valueOf(idWorker.nextId()));
                //设置sku规格
                if (sku.getSpec() == null || "".equals(sku.getSpec())) {
                    sku.setSpec("{}");
                }
                //设置sku名称(商品名称 + 规格)
                String name = spu.getName();
                //将规格json字符串转换成Map
                Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                if (specMap != null && specMap.size() > 0) {
                    for (String value : specMap.values()) {
                        name += " " + value;
                    }
                }

                sku.setName(name);//名称
                sku.setSpuId(spu.getId());//设置spu的ID
                sku.setCreateTime(LocalDateTime.now());//创建日期
                sku.setUpdateTime(LocalDateTime.now());//修改日期
                sku.setCategoryId(category.getId());//商品分类ID
                sku.setCategoryName(category.getName());//商品分类名称
                sku.setBrandName(brand.getName());//品牌名称
                skuMapper.insert(sku);//插入sku表数据
            }
        }

    }
}
