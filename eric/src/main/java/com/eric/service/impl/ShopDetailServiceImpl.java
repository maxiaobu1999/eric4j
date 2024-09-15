
package com.eric.service.impl;

import com.eric.repository.IShopDetailDao;
import com.eric.repository.entity.ShopDetail;
import com.eric.service.ShopDetailService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ShopDetailServiceImpl implements ShopDetailService {
	@Resource
	private IShopDetailDao mShopDetailDao;

    @Override
    public void updateShopDetail(ShopDetail shopDetail, ShopDetail dbShopDetail) {
    	// 更新除数据库中的信息，再删除图片
		mShopDetailDao.updateById(shopDetail);
//    	if (!Objects.equals(dbShopDetail.getShopLogo(), shopDetail.getShopLogo())) {
//    		// 删除logo
//    		attachFileService.deleteFile(shopDetail.getShopLogo());
//    	}
//    	// 店铺图片
//    	String shopPhotos = shopDetail.getShopPhotos();
//    	String[] shopPhotoArray =StrUtil.isBlank(shopPhotos)?new String[]{}: shopPhotos.split(",");
//
//    	// 数据库中的店铺图片
//    	String dbShopPhotos = dbShopDetail.getShopPhotos();
//    	String[] dbShopPhotoArray =StrUtil.isBlank(dbShopPhotos)?new String[]{}: dbShopPhotos.split(",");
//    	for (String dbShopPhoto : dbShopPhotoArray) {
//    		// 如果新插入的图片中没有旧数据中的图片，则删除旧数据中的图片
//			if (!ArrayUtil.contains(shopPhotoArray, dbShopPhoto)) {
//				attachFileService.deleteFile(dbShopPhoto);
//			}
//		}
    }

	@Override
	public void deleteShopDetailByShopId(Long shopId) {
		mShopDetailDao.deleteById(shopId);
	}

	@Override
	public ShopDetail getShopDetailByShopId(Long shopId) {
		return mShopDetailDao.selectById(shopId).get(0);
	}

	@Override
	public void removeShopDetailCacheByShopId(Long shopId) {
	}
}
