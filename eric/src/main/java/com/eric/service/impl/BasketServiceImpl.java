package com.eric.service.impl;

import com.eric.repository.IBasketDao;
import com.eric.repository.dto.ShopCartDto;
import com.eric.repository.dto.ShopCartItemDiscountDto;
import com.eric.repository.dto.ShopCartItemDto;
import com.eric.repository.entity.Basket;
import com.eric.repository.param.ChangeShopCartParam;
import com.eric.repository.param.OrderItemParam;
import com.eric.repository.param.ShopCartParam;
import com.eric.service.BasketService;
import com.eric.utils.Arith;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class BasketServiceImpl implements BasketService {
    @Resource
    private IBasketDao mDao;

    @Override
    public void deleteShopCartItemsByBasketIds(String userId, List<Long> basketIds) {
        int i = mDao.deleteShopCartItemsByBasketIds(userId, basketIds);
    }

    @Override
    public void addShopCartItem(ChangeShopCartParam param, String userId) {
        Basket basket = new Basket();
        basket.setBasketCount(param.getCount());
        basket.setBasketDate(new Date());
        basket.setProdId(param.getProdId());
        basket.setShopId(param.getShopId());
        basket.setUserId(userId);
        basket.setSkuId(param.getSkuId());
        basket.setDistributionCardNo(param.getDistributionCardNo());
        mDao.insert(basket);
    }

    @Override
    public void updateShopCartItem(Basket basket) {
        mDao.updateById(basket);
    }

    @Override
    public void updateShopCartItemCount(Basket basket) {
        mDao.updateBasketCount(basket);
    }

    @Override
    public void deleteAllShopCartItems(String userId) {
        mDao.deleteAll(userId);

    }

    @Override
    public List<ShopCartItemDto> getShopCartItems(String userId) {
        List<ShopCartItemDto> shopCartItemDtoList = mDao.getShopCartItems(userId);
        for (ShopCartItemDto shopCartItemDto : shopCartItemDtoList) {
            if (shopCartItemDto.getProdCount() != null && shopCartItemDto.getPrice() != null) {
                shopCartItemDto.setProductTotalAmount(Arith.mul(shopCartItemDto.getProdCount(), shopCartItemDto.getPrice()));
            }
        }
        return shopCartItemDtoList;
    }

    @Override
    public List<ShopCartItemDto> getShopCartExpiryItems(String userId) {
        return Collections.emptyList();
    }

    @Override
    public void cleanExpiryProdList(String userId) {

    }

    @Override
    public void updateBasketByShopCartParam(String userId, Map<Long, ShopCartParam> basketIdShopCartParamMap) {

    }

    @Override
    public void removeShopCartItemsCacheByUserId(String userId) {

    }

    @Override
    public List<String> listUserIdByProdId(Long prodId) {
        return Collections.emptyList();
    }

    @Override
    public List<ShopCartDto> getShopCarts(List<ShopCartItemDto> shopCartItems) {
        // 根据店铺ID划分item
        Map<Long, List<ShopCartItemDto>> shopCartMap = shopCartItems.stream().collect(Collectors.groupingBy(ShopCartItemDto::getShopId));

        // 返回一个店铺的所有信息
        List<ShopCartDto> shopCartDtos = Lists.newArrayList();
        for (Long shopId : shopCartMap.keySet()) {
            //获取店铺的所有商品项
            List<ShopCartItemDto> shopCartItemDtoList = shopCartMap.get(shopId);

            // 构建每个店铺的购物车信息
            ShopCartDto shopCart = new ShopCartDto();

            //店铺信息
            shopCart.setShopId(shopId);
            shopCart.setShopName(shopCartItemDtoList.get(0).getShopName());

            // 对数据进行组装
            List<ShopCartItemDiscountDto> shopCartItemDiscountDtoList = Lists.newArrayList();
            ShopCartItemDiscountDto shopCartItemDiscountDto = new ShopCartItemDiscountDto();
            shopCartItemDiscountDto.setShopCartItems(shopCartItemDtoList);
            shopCartItemDiscountDtoList.add(shopCartItemDiscountDto);
            shopCart.setShopCartItemDiscounts(shopCartItemDiscountDtoList);

            shopCartDtos.add(shopCart);
        }

        return shopCartDtos;
    }

    @Override
    public List<ShopCartItemDto> getShopCartItemsByOrderItems(List<Long> basketId, OrderItemParam orderItem, String userId) {
        return Collections.emptyList();
    }
}
