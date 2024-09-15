package com.eric.service;


import com.eric.repository.entity.ShopDetail;

/**
 *
 * @author lgh on 2018/08/29.
 */
public interface ShopDetailService {
	/**
	 * 更新店铺信息
	 * @param shopDetail
	 * @param dbShopDetail
	 */
	void updateShopDetail(ShopDetail shopDetail, ShopDetail dbShopDetail);

	/**
	 * 删除店铺详情
	 * @param id
	 */
	void deleteShopDetailByShopId(Long id);

	/**
	 * 根据店铺id获取店铺信息
	 * @param shopId
	 * @return
	 */
	ShopDetail getShopDetailByShopId(Long shopId);

	/**
	 * 根据店铺id删除店铺详情信息
	 * @param shopId
	 */
	void removeShopDetailCacheByShopId(Long shopId);
}
