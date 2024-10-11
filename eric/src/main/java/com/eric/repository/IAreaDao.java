package com.eric.repository;

import com.eric.repository.entity.Area;
import com.eric.repository.entity.TransfeeFree;
import com.eric.repository.entity.Transport;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地址选择
 */
@Repository
public interface IAreaDao {
    @Select("SELECT * FROM tz_area WHERE parent_id=#{pid}")
    List<Area> selectList(Long pid);

    @Select("SELECT * FROM tz_area WHERE area_id=#{id}")
    Area getById(Long id);

    @Insert("INSERT INTO tz_area(area_name,parent_id,level)" +
            "values(#{areaName},#{parentId},#{level})")
    int insert(Area area);

    @Update("UPDATE tz_area SET " +
            "area_name=#{areaName},parent_id=#{parentId},level=#{level} " +
            "WHERE area_id=#{areaId} ")
    int updateById(Area area);

    @Delete("DELETE FROM tz_area WHERE area_id=#{areaId} ")
    int removeById(Long areaId);
}
