package com.eric.controller;

import cn.hutool.core.bean.BeanUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.UserAddrDto;
import com.eric.repository.entity.UserAddr;
import com.eric.repository.param.AddrParam;
import com.eric.service.UserAddrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/address")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "地址接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class AddrController  extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private UserAddrService mUserAddrService;


    /**
     * 选择订单配送地址
     */

    @ApiOperation("用户地址列表 获取用户的所有地址信息")
    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public BaseResponse<List<UserAddrDto>> dvyList(HttpServletRequest request) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("list" + ",userId:" + userId);
        List<UserAddr> userAddr = mUserAddrService.list(userId);
        return BaseResponse.success(BeanUtil.copyToList(userAddr, UserAddrDto.class));
    }

    @ApiOperation("新增用户地址")
    @RequestMapping(value = {"/addAddr"}, method = {RequestMethod.POST})
    public BaseResponse<String> addAddr(HttpServletRequest request,@Valid @RequestBody AddrParam addrParam) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("addAddr" + ",userId:" + userId);
        if (addrParam.getAddrId() != null && addrParam.getAddrId() != 0) {
            return BaseResponse.fail("该地址已存在");
        }
        long addrCount = mUserAddrService.list( userId).size();
        UserAddr userAddr = BeanUtil.copyProperties(addrParam, UserAddr.class);

        if (addrCount == 0) {
            userAddr.setCommonAddr(1);
        } else {
            userAddr.setCommonAddr(0);
        }
        userAddr.setUserId(userId);
        userAddr.setStatus(1);
        userAddr.setCreateTime(new Date());
        userAddr.setUpdateTime(new Date());
        mUserAddrService.save(userAddr);
        if (userAddr.getCommonAddr() == 1) {
            // 清除默认地址缓存
            mUserAddrService.removeUserAddrByUserId(0L, userId);
        }
        return BaseResponse.success("添加地址成功");
    }

    @ApiOperation("修改订单用户地址 修改用户地址")
    @RequestMapping(value = {"/updateAddr"}, method = {RequestMethod.POST})
    public BaseResponse<String> updateAddr(HttpServletRequest request,@Valid @RequestBody AddrParam addrParam) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("updateAddr" + ",userId:" + userId);
        UserAddr dbUserAddr = mUserAddrService.getUserAddrByUserId(addrParam.getAddrId(), userId);
        if (dbUserAddr == null) {
            return BaseResponse.fail("该地址已被删除");
        }

        UserAddr userAddr = BeanUtil.copyProperties(addrParam, UserAddr.class);
        userAddr.setUserId(userId);
        userAddr.setUpdateTime(new Date());
        mUserAddrService.updateById(userAddr);
        // 清除当前地址缓存
        mUserAddrService.removeUserAddrByUserId(addrParam.getAddrId(), userId);
        // 清除默认地址缓存
        mUserAddrService.removeUserAddrByUserId(0L, userId);
        return BaseResponse.success("修改地址成功");
    }



    @ApiOperation("删除订单用户地址 根据地址id，删除用户地址")
    @ApiImplicitParam(name = "addrId", value = "地址ID" , required = true)
    @RequestMapping(value = {"/deleteAddr/{addrId}"}, method = {RequestMethod.POST})
    public BaseResponse<String> deleteDvy(HttpServletRequest request,@PathVariable("addrId") Long addrId) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("updateAddr" + ",userId:" + userId);
        UserAddr userAddr = mUserAddrService.getUserAddrByUserId(addrId, userId);
        if (userAddr == null) {
            return BaseResponse.fail("该地址已被删除");
        }
        if (userAddr.getCommonAddr() == 1) {
            return BaseResponse.fail("默认地址无法删除");
        }
        mUserAddrService.removeById(addrId);
        mUserAddrService.removeUserAddrByUserId(addrId, userId);
        return BaseResponse.success("删除地址成功");
    }


    @ApiOperation("设置默认地址 ")
    @Operation(summary = "设置默认地址" , description = "根据地址id，设置默认地址")
    @ApiImplicitParam(name = "addrId", value = "地址ID" , required = true)
    @RequestMapping(value = {"/defaultAddr/{addrId}"}, method = {RequestMethod.POST})
    public BaseResponse<String> defaultAddr(HttpServletRequest request,@PathVariable("addrId") Long addrId) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("defaultAddr" + ",userId:" + userId);
        mUserAddrService.updateDefaultUserAddr(addrId, userId);

        mUserAddrService.removeUserAddrByUserId(0L, userId);
        mUserAddrService.removeUserAddrByUserId(addrId, userId);
        return BaseResponse.success("设置默认地址成功");
    }

    @ApiOperation("获取地址信息 ")
    @Operation(summary = "获取地址信息" , description = "根据地址id，获取地址信息")
    @ApiImplicitParam(name = "addrId", value = "地址ID" , required = true)
    @RequestMapping(value = {"/addrInfo/{addrId}"}, method = {RequestMethod.GET})
    public BaseResponse<UserAddrDto> addrInfo(HttpServletRequest request,@PathVariable("addrId") Long addrId) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("addrInfo" + ",userId:" + userId);
        UserAddr userAddr = mUserAddrService.getUserAddrByUserId(addrId, userId);
        if (userAddr == null) {
            return BaseResponse.fail("该地址已被删除");
        }
        return BaseResponse.success(BeanUtil.copyProperties(userAddr, UserAddrDto.class));
    }
}
