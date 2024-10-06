package com.eric.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.exception.BusinessException;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.ProductDto;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;
import com.eric.repository.entity.Transport;
import com.eric.repository.param.ProductParam;
import com.eric.service.ProdTagReferenceService;
import com.eric.service.ProductService;
import com.eric.service.SkuService;
import com.eric.service.TransportService;
import com.eric.utils.Json;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@RequestMapping(value = "/prod")// 配置url映射,一级
@Api(value = "activity", description = "商品接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class ProductController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductService mProductService;
    @Resource
    private SkuService mSkuService;
    @Resource
    private TransportService mTransportService;
    @Resource
    private ProdTagReferenceService mProdTagReferenceService;
    /**
     * 获取信息
     */
    @ApiOperation("通过id商品信息")
    @ApiImplicitParam(name = "Product", value = "商品id", paramType = "body", required = true, dataType = "Product")
    @RequestMapping(value = {"/info"}, method = {RequestMethod.GET})
    public BaseResponse<ProductDto> info(@ApiParam("商品ID") Long prodId) {
        logger.info("info: prodId={}", prodId);
        BaseResponse<ProductDto> responseEntity;
        try {
            logger.info("info" + ",prodId:" + prodId);
            Product product = mProductService.getItem(prodId);
            if (product == null) {
                return BaseResponse.success();
            }
            // 启用的sku列表
            List<Sku> skuList = mSkuService.listByProdId(prodId);
            product.setSkuList(skuList);
            ProductDto productDto = BeanUtil.copyProperties(product, ProductDto.class);
            // 商品的配送方式
            Product.DeliveryModeVO deliveryModeVO = Json.parseObject(product.getDeliveryMode(), Product.DeliveryModeVO.class);
            // 有店铺配送的方式, 且存在运费模板，才返回运费模板的信息，供前端查阅
            if (deliveryModeVO.getHasShopDelivery()  && product.getDeliveryTemplateId() != null) {
                Transport transportAndAllItems = mTransportService.getTransportAndAllItems(product.getDeliveryTemplateId());
                productDto.setTransport(transportAndAllItems);
            }

            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(productDto);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : " + e.getMessage());
        }
        return responseEntity;
    }

    /**
     * 获取信息
     */
    @ApiOperation("sys通过id商品信息")
    @ApiImplicitParam(name = "Product", value = "商品id", paramType = "body", required = true, dataType = "Product")
    @RequestMapping(value = {"/sys/info"}, method = {RequestMethod.GET})
    public BaseResponse<Product> sysInfo(@ApiParam("商品ID") Long prodId) {
        logger.info("sysInfo: prodId={}", prodId);
        try {
            Product product = mProductService.getItem(prodId);
            if (product == null) {
                return BaseResponse.success();
            }
            // 启用的sku列表
            List<Sku> skuList = mSkuService.listByProdId(prodId);
            product.setSkuList(skuList);

            //获取分组标签
            List<Long> listTagId = mProdTagReferenceService.listTagIdByProdId(prodId);
            product.setTagList(listTagId);
            return BaseResponse.success(product);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.fail(e.getMessage());
        }
    }

    /**
     * 保存
     */
    @PostMapping
    public BaseResponse<String> save(HttpServletRequest request, @RequestBody ProductParam productParam) {
        checkParam(productParam);
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        Product product = BeanUtil.copyProperties(productParam, Product.class);
        product.setDeliveryMode(Json.toJsonString(productParam.getDeliveryModeVo()));
        product.setShopId(1L);
        product.setUpdateTime(new Date());
        if (product.getStatus() == 1) {
            product.setPutawayTime(new Date());
        }
        product.setCreateTime(new Date());
        mProductService.saveProduct(product);
        return BaseResponse.success();
    }


    /**
     * 获取商品分页信息
     */
    @RequestMapping(value = {"/page"}, method = { RequestMethod.POST})
    public BaseResponse<List<Product>> selectAll(int pageNum, int pageSize) {
        BaseResponse<List<Product>> responseEntity;
        try {
            logger.info("page" + ",pageNum:" + pageNum);
            PageHelper.startPage(pageNum, pageSize);
            List<Product> list = mProductService.selectAll();
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : " + e.getMessage());
        }
        return responseEntity;
    }


    /**
     * 新品推荐
     */
    @RequestMapping(value = {"/newProd"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<List<Product>> selectRange() {

        BaseResponse<List<Product>> responseEntity;
        try {
            logger.info("newProd");
            List<Product> list = mProductService.selectRange(100, 105);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : " + e.getMessage());
        }
        return responseEntity;
    }

    private void checkParam(ProductParam productParam) {
        if (CollectionUtil.isEmpty(productParam.getTagList())) {
            throw new RuntimeException("请选择产品分组");
        }

        Product.DeliveryModeVO deliveryMode = productParam.getDeliveryModeVo();
        boolean hasDeliverMode = deliveryMode != null
                && (deliveryMode.getHasShopDelivery() || deliveryMode.getHasUserPickUp());
        if (!hasDeliverMode) {
            throw new RuntimeException("请选择配送方式");
        }
        List<Sku> skuList = productParam.getSkuList();
        boolean isAllUnUse = true;
        for (Sku sku : skuList) {
            if (sku.getStatus() == 1) {
                isAllUnUse = false;
            }
        }
        if (isAllUnUse) {
            throw new RuntimeException("至少要启用一种商品规格");
        }
    }
}
