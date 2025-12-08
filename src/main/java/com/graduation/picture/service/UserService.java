package com.graduation.picture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.UserAddDTO;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 用户登录
     * @param account 用户账号
     * @param password 用户密码
     * @param request
     * @return
     */
    LoginUserVO userLogin(String account, String password, HttpServletRequest request);

    /**
     * 获得脱敏后的登录用户信息
     *
     * @param user
     * @return LoginUserVO
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 创建用户
     * @param userAddDTO 用户信息
     * @return
     */
    long addUser(UserAddDTO userAddDTO);
}
