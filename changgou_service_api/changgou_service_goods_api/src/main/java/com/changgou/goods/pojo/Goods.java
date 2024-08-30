package com.changgou.goods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/28/21:23
 * @ Description:
 */
@Data
public class Goods implements Serializable {
    private Spu spu;
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
