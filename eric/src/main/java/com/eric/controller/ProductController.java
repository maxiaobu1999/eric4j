package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.repository.entity.Product;
import com.eric.response.ServerResponseEntity;
import com.eric.service.ProductService;
import com.eric.service.SmsCodeService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
    private ProductService mService;

    /**
     * 获取信息
     */
    @RequestMapping(value = {"/info"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<Product> info(Long prodId) {
        logger.info("info: prodId={}", prodId );
        BaseResponse<Product> responseEntity;
        try {
            logger.info("info" + ",prodId:" + prodId);
            Product prod = mService.getItem(prodId);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(prod);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }


    /**
     * 获取信息
     */
    @RequestMapping(value = {"/page"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<List<Product>> selectAll(int pageNum,int pageSize) {

        BaseResponse<List<Product>> responseEntity;
        try {
            logger.info("page" + ",pageNum:" + pageNum);
            PageHelper.startPage(pageNum,pageSize);
            List<Product> list = mService.SelectAll();
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
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
            logger.info("newProd" );
            List<Product> list = mService.selectRange(100, 105);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }


//    @PostMapping("/adminLogin")
//    @Operation(summary = "账号密码 + 验证码登录(用于后台登录)" , description = "通过账号/手机号/用户名密码登录")
//    public ServerResponseEntity<?> login(
//            @Valid @RequestBody CaptchaAuthenticationDTO captchaAuthenticationDTO) {
//        // 登陆后台登录需要再校验一遍验证码
//        CaptchaVO captchaVO = new CaptchaVO();
//        captchaVO.setCaptchaVerification(captchaAuthenticationDTO.getCaptchaVerification());
//        ResponseModel response = captchaService.verification(captchaVO);
//        if (!response.isSuccess()) {
//            return ServerResponseEntity.showFailMsg("验证码有误或已过期");
//        }
//
//        SysUser sysUser = sysUserService.getByUserName(captchaAuthenticationDTO.getUserName());
//        if (sysUser == null) {
//            throw new YamiShopBindException("账号或密码不正确");
//        }
//
//        // 半小时内密码输入错误十次，已限制登录30分钟
//        String decryptPassword = passwordManager.decryptPassword(captchaAuthenticationDTO.getPassWord());
//        passwordCheckManager.checkPassword(SysTypeEnum.ADMIN,captchaAuthenticationDTO.getUserName(), decryptPassword, sysUser.getPassword());
//
//        // 不是店铺超级管理员，并且是禁用状态，无法登录
//        if (Objects.equals(sysUser.getStatus(),0)) {
//            // 未找到此用户信息
//            throw new YamiShopBindException("未找到此用户信息");
//        }
//
//        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
//        userInfoInToken.setUserId(String.valueOf(sysUser.getUserId()));
//        userInfoInToken.setSysType(SysTypeEnum.ADMIN.value());
//        userInfoInToken.setEnabled(sysUser.getStatus() == 1);
//        userInfoInToken.setPerms(getUserPermissions(sysUser.getUserId()));
//        userInfoInToken.setNickName(sysUser.getUsername());
//        userInfoInToken.setShopId(sysUser.getShopId());
//        // 存储token返回vo
//        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
//        return ServerResponseEntity.success(tokenInfoVO);
//    }
}
