package com.graduation.picture.controller;

import cn.hutool.core.util.ObjectUtil;
import com.graduation.picture.common.BaseResponse;
import com.graduation.picture.common.DeleteRequest;
import com.graduation.picture.common.ResultUtils;
import com.graduation.picture.exception.BusinessException;
import com.graduation.picture.exception.ErrorCode;
import com.graduation.picture.exception.ThrowUtils;
import com.graduation.picture.manager.auth.annotation.SaSpaceCheckPermission;
import com.graduation.picture.manager.auth.model.SpaceUserPermissionConstant;
import com.graduation.picture.model.dto.spaceuser.SpaceUserAddDTO;
import com.graduation.picture.model.dto.spaceuser.SpaceUserEditDTO;
import com.graduation.picture.model.qo.SpaceUserQueryQo;
import com.graduation.picture.model.entity.SpaceUser;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.vo.SpaceUserVO;
import com.graduation.picture.service.SpaceService;
import com.graduation.picture.service.SpaceUserService;
import com.graduation.picture.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname SpaceUserController
 * @Date 2025/12/31 15:17
 * @Author by Eddie
 */
@RestController
@RequestMapping("/spaceUser")
@Slf4j
public class SpaceUserController {

    @Resource
    private SpaceUserService spaceUserService;

    @Resource
    private UserService userService;

    @Resource
    private SpaceService spaceService;

    /**
     * 添加成员到空间
     */
    @PostMapping("/add")
    @SaSpaceCheckPermission(value = SpaceUserPermissionConstant.SPACE_USER_MANAGE)
    public BaseResponse<Long> addSpaceUser(@RequestBody SpaceUserAddDTO spaceUserAddDTO, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceUserAddDTO == null, ErrorCode.PARAMS_ERROR);
        long id = spaceUserService.addSpaceUser(spaceUserAddDTO);
        return ResultUtils.success(id);
    }

    /**
     * 从空间移除成员
     */
    @PostMapping("/delete")
    @SaSpaceCheckPermission(value = SpaceUserPermissionConstant.SPACE_USER_MANAGE)
    public BaseResponse<Boolean> deleteSpaceUser(@RequestBody DeleteRequest deleteRequest,
                                                 HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        SpaceUser oldSpaceUser = spaceUserService.getById(id);
        ThrowUtils.throwIf(oldSpaceUser == null, ErrorCode.NOT_FOUND_ERROR);
        // 判断移除成员是否为空间创建者，若是空间创建者，则不许移除
        boolean isSpaceCreator = spaceService.isSpaceCreator(oldSpaceUser.getSpaceId(), oldSpaceUser.getUserId());
        ThrowUtils.throwIf(isSpaceCreator, ErrorCode.NOT_DELETE_SPACE_CREATOR_ERROR);
        // 操作数据库
        boolean result = spaceUserService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 查询某个成员在某个空间的信息
     */
    @PostMapping("/get")
    @SaSpaceCheckPermission(value = SpaceUserPermissionConstant.SPACE_USER_MANAGE)
    public BaseResponse<SpaceUser> getSpaceUser(@RequestBody SpaceUserQueryQo spaceUserQueryQo) {
        // 参数校验
        ThrowUtils.throwIf(spaceUserQueryQo == null, ErrorCode.PARAMS_ERROR);
        Long spaceId = spaceUserQueryQo.getSpaceId();
        Long userId = spaceUserQueryQo.getUserId();
        ThrowUtils.throwIf(ObjectUtil.hasEmpty(spaceId, userId), ErrorCode.PARAMS_ERROR);
        // 查询数据库
        SpaceUser spaceUser = spaceUserService.getOne(spaceUserService.getQueryWrapper(spaceUserQueryQo));
        ThrowUtils.throwIf(spaceUser == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(spaceUser);
    }

    /**
     * 查询成员信息列表
     */
    @PostMapping("/list")
    @SaSpaceCheckPermission(value = SpaceUserPermissionConstant.SPACE_USER_MANAGE)
    public BaseResponse<List<SpaceUserVO>> listSpaceUser(@RequestBody SpaceUserQueryQo spaceUserQueryQo,
                                                         HttpServletRequest request) {
        ThrowUtils.throwIf(spaceUserQueryQo == null, ErrorCode.PARAMS_ERROR);
        List<SpaceUser> spaceUserList = spaceUserService.list(
                spaceUserService.getQueryWrapper(spaceUserQueryQo)
        );
        return ResultUtils.success(spaceUserService.getSpaceUserVOList(spaceUserList));
    }

    /**
     * 编辑成员信息（设置权限）
     */
    @PostMapping("/edit")
    @SaSpaceCheckPermission(value = SpaceUserPermissionConstant.SPACE_USER_MANAGE)
    public BaseResponse<Boolean> editSpaceUser(@RequestBody SpaceUserEditDTO spaceUserEditDTO,
                                               HttpServletRequest request) {
        if (spaceUserEditDTO == null || spaceUserEditDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        SpaceUser spaceUser = new SpaceUser();
        BeanUtils.copyProperties(spaceUserEditDTO, spaceUser);
        // 数据校验
        spaceUserService.validSpaceUser(spaceUser, false);
        // 判断是否存在
        long id = spaceUserEditDTO.getId();
        SpaceUser oldSpaceUser = spaceUserService.getById(id);
        ThrowUtils.throwIf(oldSpaceUser == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceUserService.updateById(spaceUser);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 查询我加入的团队空间列表
     */
    @PostMapping("/list/my")
    public BaseResponse<List<SpaceUserVO>> listMyTeamSpace(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        SpaceUserQueryQo spaceUserQueryQo = new SpaceUserQueryQo();
        spaceUserQueryQo.setUserId(loginUser.getId());
        List<SpaceUser> spaceUserList = spaceUserService.list(
                spaceUserService.getQueryWrapper(spaceUserQueryQo)
        );
        return ResultUtils.success(spaceUserService.getSpaceUserVOList(spaceUserList));
    }
}