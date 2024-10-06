/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.eric.service;



import com.eric.repository.entity.SysMenu;

import java.util.List;


/**
 * 菜单管理
 * @author lgh
 */
public interface SysMenuService  {

	/**
	 * 获取用户菜单列表
	 * @param userId 用户id
	 * @return 菜单列表
	 */
	List<SysMenu> listMenuByUserId(String userId);

}
