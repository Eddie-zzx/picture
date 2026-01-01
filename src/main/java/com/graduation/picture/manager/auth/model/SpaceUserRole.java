package com.graduation.picture.manager.auth.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname SpaceUserRole
 * @Date 2025/12/31 15:30
 * @Author by Eddie
 */
@Data
public class SpaceUserRole implements Serializable {

    /**
     * 角色键
     */
    private String key;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 权限键列表
     */
    private List<String> permissions;

    /**
     * 角色描述
     */
    private String description;

    private static final long serialVersionUID = 1L;
}
