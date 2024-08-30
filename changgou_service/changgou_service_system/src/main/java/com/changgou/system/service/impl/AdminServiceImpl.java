package com.changgou.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changgou.system.dao.AdminMapper;
import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Admin> findAll() {
        return adminMapper.selectList(null);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Admin findById(Integer id) {
        return adminMapper.selectById(id);
    }


    /**
     * 增加
     *
     * @param admin
     */
    @Override
    public void add(Admin admin) {
        String password = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(password);
        adminMapper.insert(admin);
    }


    /**
     * 修改
     *
     * @param admin
     */
    @Override
    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        adminMapper.deleteById(id);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Admin> findList(Map<String, Object> searchMap) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>();
        wrapper.like(searchMap.get("loginName") != null && !"".equals(searchMap.get("loginName")), Admin::getLoginName, searchMap.get("loginName"))
                .eq(searchMap.get("status") != null && !"".equals(searchMap.get("status")), Admin::getStatus, searchMap.get("status"));
        List<Admin> adminList = adminMapper.selectList(wrapper);
        return adminList;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Admin> findPage(int page, int size) {
        Page<Admin> brandPage = new Page<>(page, size);
        return adminMapper.selectPage(brandPage, null);
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Admin> findPage(Map<String, Object> searchMap, int page, int size) {
        Page<Admin> brandPage = new Page<>(page, size);
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>();
        wrapper.like(searchMap.get("loginName") != null && !"".equals(searchMap.get("loginName")), Admin::getLoginName, searchMap.get("loginName"))
                .eq(searchMap.get("status") != null && !"".equals(searchMap.get("status")), Admin::getStatus, searchMap.get("status"));
        return adminMapper.selectPage(brandPage, wrapper);
    }

    @Override
    public boolean login(Admin admin) {
        //根据登录名查询管理员
        Admin admin1 = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getLoginName, admin.getLoginName()));//数据库查询出的对象
        if (admin1 == null) {
            return false;
        } else {
            //验证密码, Bcrypt为spring的包, 第一个参数为明文密码, 第二个参数为密文密码
            return BCrypt.checkpw(admin.getPassword(), admin1.getPassword());
        }
    }


}
