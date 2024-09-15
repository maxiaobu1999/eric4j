
package com.eric.service;


import com.eric.repository.entity.UserAddrOrder;

/**
 * 订单地址
 */
public interface UserAddrOrderService {
	/**
	 * 把订单地址保存到数据库
	 * @return
	 */
	void save(UserAddrOrder userAddrOrder);


	UserAddrOrder getById(Long orderNumber);

}

