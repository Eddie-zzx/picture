package com.graduation.picture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.entity.User;

/**
 * @Classname UserService
 * @Date 2025/12/8 10:11
 * @Author by Eddie
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param account 用户账号
     * @param password 用户密码
     * @param checkPassword 校验
     * @return 新用户id
     */
    long userRegister(String account, String password, String checkPassword);

    /**
     * 获取加密后的密码
     *
     * @param password
     * @return
     */
    String getEncryptPassword(String password);
}
