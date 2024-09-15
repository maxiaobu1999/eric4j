
package com.eric.service;


import com.eric.repository.dto.MyOrderDto;

import java.util.List;

/**
 * 我的订单
 */
public interface MyOrderService{

	/**
	 * 通过用户id和订单状态分页获取订单信息
	 * @param userId 用户id
	 * @param status 订单状态
	 * @return
	 */
	List<MyOrderDto> pageMyOrderByUserIdAndStatus(String userId, Integer status);
}
