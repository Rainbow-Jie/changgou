package com.changgou.service.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/28/21:36
 * @ Description:
 */
public interface ISpuService extends IService<Spu> {
    /***
     * 新增
     * @param goods
     */
    void add(Goods goods);

    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    Goods findGoodsById(String id);

    /***
     * 修改数据
     * @param goods
     */
    void update(Goods goods);

    /**
     * 审核
     * @param id
     */
    void audit(String id);

    /**
     * 下架商品
     * @param id
     */
   void pull(String id);

    /**
     * 上架商品
     * @param id
     */
    void put(String id);


    void delete(String id);

    /**
     * 恢复数据
     * @param id
     */
    void restore(String id);

    /**
     * 物理删除
     * @param id
     */
    void realDelete(String id);
}
