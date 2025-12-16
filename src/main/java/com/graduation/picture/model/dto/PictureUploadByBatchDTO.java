package com.graduation.picture.model.dto;

import lombok.Data;

@Data
public class PictureUploadByBatchDTO {
  
    /**  
     * 搜索词  
     */  
    private String searchText;

    /**
     * 名称前缀
     */
    private String namePrefix;

    /**  
     * 抓取数量  
     */  
    private Integer count = 10;  
}
