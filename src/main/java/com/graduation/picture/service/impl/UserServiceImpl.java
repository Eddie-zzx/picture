package com.graduation.picture.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.picture.enums.UserRoleEnum;
import com.graduation.picture.exception.BusinessException;
import com.graduation.picture.exception.ErrorCode;
import com.graduation.picture.mapper.UserMapper;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @Classname UserServiceImpl
 * @Date 2025/12/8 10:11
 * @Author by Eddie
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private static final int USER_ACCOUNT_LENGTH = 4;
    private static final int USER_PASSWORD_LENGTH = 8;


    @Override
    public long userRegister(String account, String password, String checkPassword) {
        // 1. 校验参数
        if (StrUtil.hasBlank(account, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (account.length() < USER_ACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (password.length() < USER_PASSWORD_LENGTH || checkPassword.length() < USER_PASSWORD_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 2. 检查用户账号是否和数据库中已有的重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", account);
        long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 3. 密码加密
        String encryptPassword = getEncryptPassword(password);
        // 4. 插入数据到数据库中
        User user = new User();
        user.setUserAccount(account);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }


    /**
     * 获取加密后的密码
     *
     * @param userPassword 用户密码
     * @return 加密后的密码
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        // 加盐，混淆密码
        final String SALT = "Eddie-zzx";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
}
