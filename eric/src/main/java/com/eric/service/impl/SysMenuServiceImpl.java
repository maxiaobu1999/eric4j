package com.eric.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.eric.controller.SysMenuController;
import com.eric.repository.SysMenuDao;
import com.eric.repository.entity.SysMenu;
import com.eric.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lgh
 */
@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class SysMenuServiceImpl implements SysMenuService {
    private static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);
    @Resource
    private SysMenuDao mSysMenuDao;

    @Override
    public List<SysMenu> listMenuByUserId(String userId) {
        // 用户的所有菜单信息
        List<SysMenu> sysMenus;
        //系统管理员，拥有最高权限
//		if(userId == Constant.SUPER_ADMIN_ID){
//			sysMenus = sysMenuMapper.listMenu();
//		}else {
        sysMenus = mSysMenuDao.listMenuByUserId(userId);
//		}

        Map<Long, List<SysMenu>> sysMenuLevelMap = sysMenus.stream()
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .collect(Collectors.groupingBy(SysMenu::getParentId));

        // 一级菜单
        List<SysMenu> rootMenu = sysMenuLevelMap.get(0L);
        if (CollectionUtil.isEmpty(rootMenu)) {
            return Collections.emptyList();
        }
		// 二级菜单
		for (SysMenu sysMenu : rootMenu) {
			sysMenu.setList(sysMenuLevelMap.get(sysMenu.getMenuId()));
		}
        return rootMenu;
    }

}
