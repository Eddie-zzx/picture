package com.graduation.picture.manager.auth.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname SpaceUserAuthConfig
 * @Date 2025/12/31 15:29
 * @Author by Eddie
 */
@Data
public class SpaceUserAuthConfig implements Serializable {

    /**
     * 权限列表
     */
    private List<SpaceUserPermission> permissions;

    /**
     * 角色列表
     */
    private List<SpaceUserRole> roles;

    private static final long serialVersionUID = 1L;
}

