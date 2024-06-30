package com.eric.controller;


import com.eric.service.SmsCodeService;
import com.eric.service.UserService;
import com.eric.utils.PasswordUtils;
import com.eric.utils.StringUtils;
import com.eric.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/prod")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class ProductController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    //
//    @Resource
//    private TokenManager mTokenManager;
    @Resource
    private SmsCodeService mSmsCodeService;
    @Resource
    private UserService mUserService;

//    /**
//     * 获取信息
//     */
//    @GetMapping("/info/{prodId}")
//    public ServerResponseEntity<Product> info(@PathVariable("prodId") Long prodId) {
//        Product prod = productService.getProductByProdId(prodId);
//        if (!Objects.equals(prod.getShopId(), SecurityUtils.getSysUser().getShopId())) {
//            throw new YamiShopBindException("没有权限获取该商品规格信息");
//        }
//        List<Sku> skuList = skuService.listByProdId(prodId);
//        prod.setSkuList(skuList);
//
//        //获取分组标签
//        List<Long> listTagId = prodTagReferenceService.listTagIdByProdId(prodId);
//        prod.setTagList(listTagId);
//        return ServerResponseEntity.success(prod);
//    }



}
