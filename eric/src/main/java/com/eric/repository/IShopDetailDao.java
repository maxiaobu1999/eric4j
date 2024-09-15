package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.ShopDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IShopDetailDao {

    /**
     * 根据userId更新
     */
    @Update("update tz_shop_detail set " +
            "shop_name=#{shopName}," + "user_id=#{userId}," + "shop_type=#{shopType}," +
            "intro=#{intro}," + "shop_notice=#{shopNotice}," + "shop_industry=#{shopIndustry}," +
            "shop_owner=#{shopOwner}," + "mobile=#{mobile}," + "tel=#{tel}," +
            "shop_lat=#{shopLat}," + "shop_lng=#{shopLng}," + "shop_address=#{shopAddress}," +
            "province=#{province}," + "city=#{city}," + "area=#{area}," +
            "pca_code=#{pcaCode}," + "shop_logo=#{shopLogo}," + "shop_photos=#{shopPhotos}," +
            "open_time=#{openTime}," + "shop_status=#{shopStatus}," + "transport_type=#{transportType}," +
            "fixed_freight=#{fixedFreight}," + "full_free_shipping=#{fullFreeShipping}," + "create_time=#{createTime}," +
             "uddata_time=#{updateTime}," +
             "is_distribution=#{isDistribution} " +
            "where shop_id=#{shopId}")
    void updateById(ShopDetail shopDetail);

    @Delete("delete from tz_shop_detail where shop_id=#{shopId} ")
    void deleteById(Long shopId);

    @Select("SELECT * FROM tz_shop_detail where shop_id=#{shopId} ")
    List<ShopDetail> selectById(Long shopId);
}
