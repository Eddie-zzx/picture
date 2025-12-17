package com.graduation.picture.controller;

import com.graduation.picture.annotation.AuthCheck;
import com.graduation.picture.common.BaseResponse;
import com.graduation.picture.common.ResultUtils;
import com.graduation.picture.constant.UserConstant;
import com.graduation.picture.exception.BusinessException;
import com.graduation.picture.exception.ErrorCode;
import com.graduation.picture.exception.ThrowUtils;
import com.graduation.picture.model.dto.SpaceUpdateDTO;
import com.graduation.picture.model.entity.Space;
import com.graduation.picture.service.SpaceService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname SpaceController
 * @Date 2025/12/16 22:04
 * @Author by Eddie
 */
@RestController
@RequestMapping("/space")
public class SpaceController {

    @Resource
    private SpaceService spaceService;

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateDTO spaceUpdateDTO) {
        if (spaceUpdateDTO == null || spaceUpdateDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateDTO, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断是否存在
        long id = spaceUpdateDTO.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

}
