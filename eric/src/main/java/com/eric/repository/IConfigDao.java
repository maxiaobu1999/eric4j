package com.eric.repository;

import com.eric.repository.entity.UserAddr;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IConfigDao {

    /**
     * 首页折现图下拉配置项
     *
     * @return List<ConfigItemVo>
     */
    @Select("SELECT  a.* " +
            "FROM tpl_config_item_t a " +
            "WHERE config_id  in (SELECT config_id  FROM tpl_config_classify_t WHERE config_code = 'homePageFilter')")
    List<ConfigItemVo> accessTypeFilter();


}
