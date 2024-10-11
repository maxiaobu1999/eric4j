
package com.eric.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 地区名称树状表
 * tz_area
 */
@Data
public class Area implements Serializable {
    private static final long serialVersionUID = -6013320537436191451L;


    @Schema(description = "地区id" ,required=true)
    private Long areaId;

    @Schema(description = "地区名称" ,required=true)
    private String areaName;

    @Schema(description = "地区上级id" ,required=true)
    private Long parentId;

    @Schema(description = "地区层级" ,required=true)
    private Integer level;

    private List<Area> areas;


}
