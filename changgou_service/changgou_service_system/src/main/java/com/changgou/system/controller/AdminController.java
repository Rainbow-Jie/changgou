package com.changgou.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Admin> adminList = adminService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",adminList) ;
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Admin admin = adminService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",admin);
    }


    /***
     * 新增数据
     * @param admin
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /***
     * 修改数据
     * @param admin
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Admin admin,@PathVariable Integer id){
        admin.setId(id);
        adminService.update(admin);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Admin> list = adminService.findList(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result findPage(@RequestParam Map searchMap, @PathVariable  int page, @PathVariable  int size){
        Page<Admin> pageList = adminService.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getRecords());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /**
     * 登录
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        boolean login = adminService.login(admin);
        if(login){
            return new Result();
        }else{
            return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
        }
    }

}
