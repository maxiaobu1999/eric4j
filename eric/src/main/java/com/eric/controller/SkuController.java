package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;
import com.eric.service.ProductService;
import com.eric.service.SkuService;
import com.eric.service.SmsCodeService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/sku")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "sku规格接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class SkuController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SkuController.class);
    @Resource
    private SkuService mService;

    /**
     * 获取信息
     */
    @Operation(summary = "通过prodId获取商品全部规格列表" , description = "通过prodId获取商品全部规格列表")
    @Parameter(name = "prodId", description = "商品id" )
    @RequestMapping(value = {"/getSkuList"}, method = {RequestMethod.GET})
    public BaseResponse<List<Sku>> info( @ApiParam("商品ID")Long prodId) {
        logger.info("info: prodId={}", prodId);
        BaseResponse<List<Sku>> responseEntity;
        try {
            logger.info("info" + ",prodId:" + prodId);
            List<Sku> prod = mService.listByProdId(prodId);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(prod);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : " + e.getMessage());
        }
        return responseEntity;
    }


}
