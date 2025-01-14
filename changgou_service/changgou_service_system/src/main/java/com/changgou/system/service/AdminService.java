package com.changgou.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.changgou.system.pojo.Admin;


import java.util.List;
import java.util.Map;

public interface AdminService extends IService<Admin> {

    /***
     * 查询所有
     * @return
     */
    List<Admin> findAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Admin findById(Integer id);

    /***
     * 新增
     * @param admin
     */
    void add(Admin admin);

    /***
     * 修改
     * @param admin
     */
    void update(Admin admin);

    /***
     * 删除
     * @param id
     */
    void delete(Integer id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Admin> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Admin> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Admin> findPage(Map<String, Object> searchMap, int page, int size);


    /**
     * 登录验证密码
     * @param admin
     * @return
     */
    boolean login(Admin admin);

}
