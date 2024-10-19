
package com.eric.service;


import com.eric.repository.dto.HotSearchDto;

import java.util.List;

/**
 * 热搜
 */
public interface HotSearchService {

    /**
     * 根据店铺id获取热搜列表
     * @param shopId
     * @return
     */
    List<HotSearchDto> getHotSearchDtoByShopId(Long shopId);

    /**
     * 根据店铺id删除热搜缓存
     * @param shopId
     */
    void removeHotSearchDtoCacheByShopId(Long shopId);
}
