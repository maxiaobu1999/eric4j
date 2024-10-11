package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.entity.*;
import com.eric.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 配送方式
 */
@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/transport")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "配送方式")
@SuppressWarnings("Duplicates") // 去除代码重复警告
@Slf4j
public class TransportController {

    @Resource
    private TransportService mTransportService;

    @Resource
    private SysUserService mSysUserService;

    /**
     * 分页获取
     */
    @GetMapping("/page")
    public BaseResponse<Page<Transport>> page(HttpServletRequest request, int pageNum, int pageSize) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        Long shopId = mSysUserService.getShopId(Long.valueOf(userId));
        Page<Transport> objects = PageHelper.startPage(pageNum, pageSize);
        mTransportService.list(shopId);
        int pageTotal = objects.getPages();
        BaseResponse<Page<Transport>> res = BaseResponse.success(objects);
        res.setTotal(pageTotal);
        return res;
    }

    /**
     * 获取信息
     */
    @GetMapping("/info/{id}")
    public BaseResponse<Transport> info(@PathVariable("id") Long id) {
        Transport transport = mTransportService.getTransportAndAllItems(id);
        return BaseResponse.success(transport);
    }

    /**
     * 保存
     */
    @PostMapping
    public BaseResponse<Void> save(HttpServletRequest request, @RequestBody Transport transport) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        Long shopId = mSysUserService.getShopId(Long.valueOf(userId));
        transport.setShopId(shopId);
        Date createTime = new Date();
        transport.setCreateTime(createTime);
        mTransportService.insertTransportAndTransfee(transport);
        return BaseResponse.success();
    }

    /**
     * 修改
     */
    @PutMapping
    public BaseResponse<Void> update(HttpServletRequest request,@RequestBody Transport transport) {
        mTransportService.updateTransportAndTransfee(transport);
        return BaseResponse.success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public BaseResponse<Void> delete(@RequestBody Long[] ids) {
        mTransportService.deleteTransportAndTransfeeAndTranscity(ids);
        // 删除运费模板的缓存
        for (Long id : ids) {
            mTransportService.removeTransportAndAllItemsCache(id);
        }
        return BaseResponse.success();
    }


    /**
     * 获取运费模板列表
     */
    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public BaseResponse<List<Transport>> list(HttpServletRequest request) {
        log.info("list:");
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        Long shopId = mSysUserService.getShopId(Long.valueOf(userId));
        List<Transport> list = mTransportService.list(shopId);
        return BaseResponse.success(list);
    }

}
