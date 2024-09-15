package com.eric.service;


import com.eric.repository.dto.PayInfoDto;
import com.eric.repository.entity.OrderSettlement;
import com.eric.repository.param.PayParam;

import java.util.List;

/**
 */
public interface PayService {

    /**
     * 支付
     * @param userId
     * @param payParam
     * @return
     */
    PayInfoDto pay(String userId, PayParam payParam);

    /**
     * 支付成功
     * @param payNo
     * @param bizPayNo 外部订单流水号
     * @return
     */
    List<String> paySuccess(String payNo, String bizPayNo);


    /**
     * 根据支付单号获取结算信息
     * @param payNo 支付单号
     * @return
     */
   OrderSettlement payInfo(String payNo);

}
