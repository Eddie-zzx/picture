package com.graduation.picture.controller;

import com.graduation.picture.common.BaseResponse;
import com.graduation.picture.common.ResultUtils;
import com.graduation.picture.exception.ErrorCode;
import com.graduation.picture.exception.ThrowUtils;
import com.graduation.picture.model.dto.UserRegisterDTO;
import com.graduation.picture.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *@Classname UserController
 *@Date 2025/12/8 10:10
 *@Author by Eddie
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        ThrowUtils.throwIf(userRegisterDTO == null, ErrorCode.PARAMS_ERROR);
        String account = userRegisterDTO.getUserAccount();
        String password = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        long result = userService.userRegister(account, password, checkPassword);
        return ResultUtils.success(result);
    }

}
