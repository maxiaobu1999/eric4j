package com.eric.repository;

import org.apache.ibatis.annotations.Select;
import com.eric.repository.dto.HotSearchDto;

import java.util.List;

/**
 * 搜索热词
 */
public interface IHotSearchDao {
    /**
     * 根据店铺id获取热搜列表
     * @param shopId
     * @return
     */
    @Select("SELECT hot_search_id, content, title FROM tz_hot_search WHERE shop_id=#{shopId} AND status=1 ORDER BY seq")
    List<HotSearchDto> getHotSearchDtoByShopId(Long shopId);

}
