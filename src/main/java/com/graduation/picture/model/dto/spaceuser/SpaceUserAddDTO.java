package com.graduation.picture.model.dto.spaceuser;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建空间成员请求
 */
@Data
public class SpaceUserAddDTO implements Serializable {

    /**
     * 空间 ID
     */
    private Long spaceId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 空间角色：viewer/editor/admin
     */
    private String spaceRole;

    private static final long serialVersionUID = 1L;
}