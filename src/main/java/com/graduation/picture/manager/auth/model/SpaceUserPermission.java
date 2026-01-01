package com.graduation.picture.manager.auth.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SpaceUserPermission
 * @Date 2025/12/31 15:30
 * @Author by Eddie
 */
@Data
public class SpaceUserPermission implements Serializable {

    /**
     * 权限键
     */
    private String key;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    private static final long serialVersionUID = 1L;

}

