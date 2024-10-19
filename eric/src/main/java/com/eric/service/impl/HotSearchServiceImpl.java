package com.eric.service.impl;

import com.eric.repository.IHotSearchDao;
import com.eric.repository.dto.HotSearchDto;
import com.eric.service.HotSearchService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class HotSearchServiceImpl implements HotSearchService {
    @Resource
    private IHotSearchDao mHotSearchDao;

    @Override
//    @Cacheable(cacheNames = "HotSearchDto", key = "#shopId")
    public List<HotSearchDto> getHotSearchDtoByShopId(Long shopId) {
        return mHotSearchDao.getHotSearchDtoByShopId(shopId);
    }

    @Override
//    @CacheEvict(cacheNames = "HotSearchDto", key = "#shopId")
    public void removeHotSearchDtoCacheByShopId(Long shopId) {

    }
}
