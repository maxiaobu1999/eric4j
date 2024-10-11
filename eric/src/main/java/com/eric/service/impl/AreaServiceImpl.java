package com.eric.service.impl;

import com.eric.repository.IAdvertisementDao;
import com.eric.repository.IAreaDao;
import com.eric.repository.entity.Area;
import com.eric.service.AreaService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private IAreaDao mAreaDao;

    @Override
    @Cacheable(cacheNames = "area", key = "#pid")
    public List<Area> listByPid(Long pid) {
        return mAreaDao.selectList(pid);
    }

    @Override
    @CacheEvict(cacheNames = "area", key = "#pid")
    public void removeAreaCacheByParentId(Long pid) {

    }

    @Override
    public Area getById(Long id) {
        return mAreaDao.getById(id);
    }

    @Override
    public int save(Area area) {
        return mAreaDao.insert(area);
    }

    @Override
    public int updateById(Area area) {
        return mAreaDao.updateById(area);
    }

    @Override
    public int removeById(Long id) {
        return mAreaDao.removeById(id);
    }
}
