package com.graduation.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.spaceuser.SpaceUserAddDTO;
import com.graduation.picture.model.qo.SpaceUserQueryQo;
import com.graduation.picture.model.entity.SpaceUser;
import com.graduation.picture.model.vo.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname SpaceUserService
 * @Date 2025/12/31 15:11
 * @Author by Eddie
 */
public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 创建空间成员
     *
     * @param spaceUserAddDTO
     * @return
     */
    long addSpaceUser(SpaceUserAddDTO spaceUserAddDTO);

    /**
     * 校验空间成员
     *
     * @param spaceUser
     * @param add       是否为创建时检验
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取空间成员包装类（单条）
     *
     * @param spaceUser
     * @param request
     * @return
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间成员包装类（列表）
     *
     * @param spaceUserList
     * @return
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);

    /**
     * 获取查询对象
     *
     * @param spaceUserQueryQo
     * @return
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryQo spaceUserQueryQo);
}

