
package com.eric.service;


import com.eric.repository.entity.OrderItem;

import java.util.List;

/**
 *
 */
public interface OrderItemService {

	/**
	 * 根据订单编号获取订单项
	 * @param orderNumber
	 * @return
	 */
	List<OrderItem> getOrderItemsByOrderNumber(String orderNumber);

}
