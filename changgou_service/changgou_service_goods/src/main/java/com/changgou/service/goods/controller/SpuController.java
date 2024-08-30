package com.changgou.service.goods.controller;

import com.changgou.common.pojo.Result;
import com.changgou.goods.pojo.Goods;
import com.changgou.service.goods.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ Author: 聂振杰
 * @ Date: 2024/08/28/21:41
 * @ Description:
 */
@RestController
@RequestMapping("")
public class SpuController {
    @Autowired
    private ISpuService iSpuService;

    @PostMapping
    public Result add(@RequestBody Goods goods) {
        iSpuService.add(goods);
        return Result.okResult();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Goods goods = iSpuService.findGoodsById(id);
        return Result.okResult(goods);
    }

    /***
     * 修改数据
     * @param goods
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Goods goods, @PathVariable String id) {
        iSpuService.update(goods);
        return Result.okResult();
    }

    /**
     * 审核
     *
     * @param id
     * @return
     */
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable String id) {
        iSpuService.audit(id);
        return Result.okResult();
    }

    /**
     * 下架
     *
     * @param id
     * @return
     */
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable String id) {
        iSpuService.pull(id);
        return Result.okResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        iSpuService.delete(id);
        return Result.okResult();
    }


    /**
     * 上架
     *
     * @param id
     * @return
     */
    @PutMapping("/put/{id}")
    public Result put(@PathVariable String id) {
        iSpuService.put(id);
        return Result.okResult();
    }

    /**
     * 恢复数据
     *
     * @param id
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable String id) {
        iSpuService.restore(id);
        return Result.okResult();
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/realDelete/{id}")
    public Result realDelete(@PathVariable String id) {
        iSpuService.realDelete(id);
        return Result.okResult();
    }
}
