
package com.eric.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanhai
 */
//@TableName("tz_area")
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



    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
