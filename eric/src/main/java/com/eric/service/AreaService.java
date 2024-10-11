package com.eric.service;


import com.eric.repository.entity.Area;
import java.util.List;

/**
 * 地址选择
 */
public interface AreaService{

    /**
     * 通过pid 查找地址接口
     *
     * @param pid 父id
     * @return
     */
    List<Area> listByPid(Long pid);

    /**
     * 通过pid 清除地址缓存
     *
     * @param pid
     */
    void removeAreaCacheByParentId(Long pid);

    Area getById(Long id);
    int save(Area area);
    int updateById(Area area);
    int removeById(Long id);


}
