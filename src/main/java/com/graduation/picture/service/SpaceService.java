package com.graduation.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.picture.model.dto.SpaceAddDTO;
import com.graduation.picture.model.entity.Space;
import com.graduation.picture.model.entity.User;
import com.graduation.picture.model.qo.SpaceQueryQo;
import com.graduation.picture.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 获取空间包装类（单条）
     *
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取空间包装类（分页）
     *
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 获取查询对象
     *
     * @param spaceQueryQo
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryQo spaceQueryQo);

    /**
     * 校验空间权限
     *
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);
}
