package com.eric.listener;

import com.eric.event.ConfirmOrderEvent;
import com.eric.repository.dto.ShopCartItemDto;
import com.eric.repository.dto.ShopCartOrderDto;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;
import com.eric.repository.entity.UserAddr;
import com.eric.repository.order.ConfirmOrderOrder;
import com.eric.repository.param.OrderParam;
import com.eric.service.ProductService;
import com.eric.service.SkuService;
import com.eric.service.UserAddrService;
import com.eric.utils.Arith;
import com.eric.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 确认订单信息时的默认操作
 * @author LGH
 */
@Component("defaultConfirmOrderListener")
@AllArgsConstructor
public class ConfirmOrderListener {

    private final UserAddrService userAddrService;

//    private final TransportManagerService transportManagerService;

    private final ProductService productService;

    private final SkuService skuService;

    /**
     * 计算订单金额
     */
    @EventListener(ConfirmOrderEvent.class)
    @Order(ConfirmOrderOrder.DEFAULT)
    public void defaultConfirmOrderEvent(ConfirmOrderEvent event) {


        ShopCartOrderDto shopCartOrderDto = event.getShopCartOrderDto();

        OrderParam orderParam = event.getOrderParam();

        String userId = SecurityUtils.getUser().getUserId();

        // 订单的地址信息
        UserAddr userAddr = userAddrService.getUserAddrByUserId(orderParam.getAddrId(), userId);

        double total = 0.0;

        int totalCount = 0;

        double transfee = 0.0;

        for (ShopCartItemDto shopCartItem : event.getShopCartItems()) {
            // 获取商品信息
            Product product = productService.getItem(shopCartItem.getProdId());
            // 获取sku信息
            Sku sku = skuService.getSkuBySkuId(shopCartItem.getSkuId());
            if (product == null || sku == null) {
                throw new RuntimeException("购物车包含无法识别的商品");
            }
            if (product.getStatus() != 1 || sku.getStatus() != 1) {
                throw new RuntimeException("商品[" + sku.getProdName() + "]已下架");
            }

            totalCount = shopCartItem.getProdCount() + totalCount;
            total = Arith.add(shopCartItem.getProductTotalAmount(), total);
            // 用户地址如果为空，则表示该用户从未设置过任何地址相关信息
            if (userAddr != null) {
                // 每个产品的运费相加
//                transfee = Arith.add(transfee, transportManagerService.calculateTransfee(shopCartItem, userAddr));
            }

            shopCartItem.setActualTotal(shopCartItem.getProductTotalAmount());
            shopCartOrderDto.setActualTotal(Arith.add(total, transfee));
            shopCartOrderDto.setTotal(total);
            shopCartOrderDto.setTotalCount(totalCount);
            shopCartOrderDto.setTransfee(transfee);
        }
    }
}
