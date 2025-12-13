package com.graduation.picture.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.picture.annotation.AuthCheck;
import com.graduation.picture.common.BaseResponse;
import com.graduation.picture.common.DeleteRequest;
import com.graduation.picture.common.ResultUtils;
import com.graduation.picture.constant.UserConstant;
import com.graduation.picture.exception.BusinessException;
import com.graduation.picture.exception.ErrorCode;
import com.graduation.picture.exception.ThrowUtils;
import com.graduation.picture.model.dto.UserAddDTO;
import com.graduation.picture.model.dto.UserLoginDTO;
import com.graduation.picture.model.dto.UserRegisterDTO;
import com.graduation.picture.model.dto.UserUpdateDTO;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.qo.UserQueryQo;
import com.graduation.picture.model.vo.LoginUserVO;
import com.graduation.picture.model.vo.UserVO;
import com.graduation.picture.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *@Classname UserController
 *@Date 2025/12/8 10:10
 *@Author by Eddie
 */
@RestController
@RequestMapping("/user")
//@Api(tags = "用户接口")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        ThrowUtils.throwIf(userRegisterDTO == null, ErrorCode.PARAMS_ERROR);
        String account = userRegisterDTO.getUserAccount();
        String password = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        long result = userService.userRegister(account, password, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginDTO == null, ErrorCode.PARAMS_ERROR);
        String account = userLoginDTO.getUserAccount();
        String password = userLoginDTO.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(account, password, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/get/login")
    @ApiOperation(value = "获取当前登录用户")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 创建用户
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddDTO userAddDTO) {
        ThrowUtils.throwIf(userAddDTO == null, ErrorCode.PARAMS_ERROR);
        long userId = userService.addUser(userAddDTO);
        return ResultUtils.success(userId);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    @ApiOperation(value = "根据 id 获取用户（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类VO
     */
    @GetMapping("/get/vo")
    @ApiOperation(value = "获取用户VO")
    public BaseResponse<UserVO> getUserVOById(long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO == null || userUpdateDTO.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     *
     * @param userQueryQo 查询请求参数
     */
    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取用户封装列表（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryQo userQueryQo) {
        ThrowUtils.throwIf(userQueryQo == null, ErrorCode.PARAMS_ERROR);
        long current = userQueryQo.getCurrent();
        long pageSize = userQueryQo.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize),
                userService.getQueryWrapper(userQueryQo));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

}
