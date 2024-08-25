package com.changgou.service.goods.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.goods.service.impl.SpecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/demo")
@RestController
public class SpecController {
    @Autowired
    private SpecServiceImpl service;

    @GetMapping("/category/{category}")
    public Result findListByCategoryName(@PathVariable String category){
        List<Map> specList = service.findListByCategoryName(category);
        return new Result(true, StatusCode.OK,"",specList);
    }
}
