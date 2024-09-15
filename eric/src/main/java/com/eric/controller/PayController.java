package com.eric.controller;

import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.PayInfoDto;
import com.eric.repository.entity.OrderSettlement;
import com.eric.repository.param.PayParam;
import com.eric.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/order")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "订单接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class PayController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Resource
    PayService mPayService;
    @ApiOperation("根据订单号生成支付流水号")
    @RequestMapping(value = {"/normalPay"}, method = {RequestMethod.POST})
    public BaseResponse<String> normalPay(HttpServletRequest request, @RequestBody PayParam payParam) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("normalPay" + ",userId:" + userId);

        PayInfoDto payInfo = mPayService.pay(userId, payParam);
        mPayService.paySuccess(payInfo.getPayNo(), "");
        return BaseResponse.success();
    }


    @ApiOperation("根据订单号进行支付")
    @Operation(summary = "根据订单号进行支付" , description = "根据订单号进行支付")
    @RequestMapping(value = {"/pay"}, method = {RequestMethod.POST})
    public BaseResponse<PayInfoDto> pay(HttpServletRequest request, @RequestBody PayParam payParam) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("pay" + ",userId:" + userId);
        PayInfoDto payInfo = mPayService.pay(userId, payParam);
        return BaseResponse.success(payInfo);
    }

    @ApiOperation("支付单号 完成支付")
    @RequestMapping(value = {"/paySuccess"}, method = {RequestMethod.POST})
    public BaseResponse<String> paySuccess(String payNo,String bizPayNo) {
        mPayService.paySuccess(payNo, bizPayNo);
        return BaseResponse.success();
    }


    @ApiOperation("根据支付单号获取结算信息")
    @RequestMapping(value = {"/payInfo"}, method = {RequestMethod.POST})
    public BaseResponse<OrderSettlement> payInfo( String payNo) {
        logger.info("payInfo" + ",payNo:" + payNo);
        OrderSettlement settlement = mPayService.payInfo(payNo);
        return BaseResponse.success(settlement);
    }
}
