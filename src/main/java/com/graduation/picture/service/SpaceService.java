package com.graduation.picture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.SpaceAddDTO;
import com.graduation.picture.model.entity.Space;
import com.graduation.picture.model.entity.User;

/**
 * @Classname SpaceService
 * @Date 2025/12/16 22:05
 * @Author by Eddie
 */
public interface SpaceService extends IService<Space> {

    /**
     * 添加空间
     * @param spaceAddDTO
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddDTO spaceAddDTO, User loginUser);

    /**
     * 校验空间是否添加
     * @param space
     * @param add
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别填充空间信息
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);
}
