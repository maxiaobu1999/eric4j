package com.eric.service.impl;

import cn.hutool.core.util.StrUtil;
import com.eric.event.PaySuccessOrderEvent;
import com.eric.repository.IOrderDao;
import com.eric.repository.IOrderSettlementDao;
import com.eric.repository.dto.PayInfoDto;
import com.eric.repository.entity.Order;
import com.eric.repository.entity.OrderSettlement;
import com.eric.repository.enums.PayType;
import com.eric.repository.param.PayParam;
import com.eric.service.PayService;
import com.eric.utils.Arith;
import com.eric.utils.Util;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private IOrderDao orderMapper;
    @Resource
    private IOrderSettlementDao orderSettlementMapper;

    @Resource
    private ApplicationEventPublisher eventPublisher;

    /**
     * 不同的订单号，同一个支付流水号
     */
    @Override
    public PayInfoDto pay(String userId, PayParam payParam) {
        // 不同的订单号的产品名称
        StringBuilder prodName = new StringBuilder();
        // 支付单号
        String payNo = String.valueOf(Util.createID());
        String[] orderNumbers = payParam.getOrderNumbers().split(StrUtil.COMMA);
        // 修改订单信息
        for (String orderNumber : orderNumbers) {
            OrderSettlement orderSettlement = new OrderSettlement();
            orderSettlement.setPayNo(payNo);
            orderSettlement.setPayType(payParam.getPayType());
            orderSettlement.setUserId(userId);
            orderSettlement.setOrderNumber(orderNumber);
            orderSettlementMapper.updateByOrderNumberAndUserId(orderSettlement);

            Order order = orderMapper.getOrderByOrderNumber(orderNumber);
            prodName.append(order.getProdName()).append(StrUtil.COMMA);
        }
        // 除了ordernumber不一样，其他都一样
        List<OrderSettlement> settlements = orderSettlementMapper.selectList(payNo);
        // 应支付的总金额
        double payAmount = 0.0;
        for (OrderSettlement orderSettlement : settlements) {
            payAmount = Arith.add(payAmount, orderSettlement.getPayAmount());
        }

        prodName.substring(0, Math.min(100, prodName.length() - 1));

        PayInfoDto payInfoDto = new PayInfoDto();
        payInfoDto.setBody(prodName.toString());
        payInfoDto.setPayAmount(payAmount);
        payInfoDto.setPayNo(payNo);
        return payInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> paySuccess(String payNo, String bizPayNo) {
        List<OrderSettlement> orderSettlements = orderSettlementMapper.selectList(payNo);

        OrderSettlement settlement = orderSettlements.get(0);

        // 订单已支付
        if (settlement.getPayStatus() == 1) {
            throw new RuntimeException("订单已支付");
        }
        // 修改订单结算信息
        if (orderSettlementMapper.updateToPay(payNo, settlement.getVersion()) < 1) {
            throw new RuntimeException("结算信息已更改");
        }


        List<String> orderNumbers = orderSettlements.stream().map(OrderSettlement::getOrderNumber).collect(Collectors.toList());

        // 将订单改为已支付状态
        orderMapper.updateByToPaySuccess(orderNumbers, PayType.WECHATPAY.value());

        List<Order> orders = orderNumbers.stream().map(orderNumber -> orderMapper.getOrderByOrderNumber(orderNumber)).collect(Collectors.toList());
//        eventPublisher.publishEvent(new PaySuccessOrderEvent(orders));
        return orderNumbers;
    }

    @Override
    public OrderSettlement payInfo(String payNo) {
        List<OrderSettlement> orderSettlements = orderSettlementMapper.selectList(payNo);
        OrderSettlement settlement = orderSettlements.get(0);
        return settlement;
    }
}
