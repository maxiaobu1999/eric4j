/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.eric.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.eric.BaseResponse;
import com.eric.repository.entity.ProdTag;
import com.eric.repository.entity.Product;
import com.eric.service.ProdTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 商品分组
 *
 * @author hzm
 * @date 2019-04-18 09:08:36
 */
@RestController
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@RequestMapping("/prod/prodTag")
public class ProdTagController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProdTagService mProdTagService;

//    /**
//     * 分页查询
//     *
//     * @param page    分页对象
//     * @param prodTag 商品分组标签
//     * @return 分页数据
//     */
//    @GetMapping("/page")
//    public ServerResponseEntity<IPage<ProdTag>> getProdTagPage(PageParam<ProdTag> page, ProdTag prodTag) {
//        IPage<ProdTag> tagPage = prodTagService.page(
//                page, new LambdaQueryWrapper<ProdTag>()
//                        .eq(prodTag.getStatus() != null, ProdTag::getStatus, prodTag.getStatus())
//                        .like(prodTag.getTitle() != null, ProdTag::getTitle, prodTag.getTitle())
//                        .orderByDesc(ProdTag::getSeq, ProdTag::getCreateTime));
//        return ServerResponseEntity.success(tagPage);
//
//    }
//

    /**
     * 通过id查询商品分组标签
     *
     * @param id id
     * @return 单个数据
     */
    @GetMapping("/info/{id}")
    public BaseResponse<ProdTag> getById(@PathVariable("id") Long id) {
        log.info("info: id={}", id );
        BaseResponse<ProdTag> responseEntity;
        try {
            log.info("info" + ",prodId:" + id);
            ProdTag prod = mProdTagService.getItem(id);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(prod);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }
//
//    /**
//     * 新增商品分组标签
//     *
//     * @param prodTag 商品分组标签
//     * @return 是否新增成功
//     */
//    @SysLog("新增商品分组标签")
//    @PostMapping
//    @PreAuthorize("@pms.hasPermission('prod:prodTag:save')")
//    public ServerResponseEntity<Boolean> save(@RequestBody @Valid ProdTag prodTag) {
//        // 查看是否相同的标签
//        List<ProdTag> list = prodTagService.list(new LambdaQueryWrapper<ProdTag>().like(ProdTag::getTitle, prodTag.getTitle()));
//        if (CollectionUtil.isNotEmpty(list)) {
//            throw new YamiShopBindException("标签名称已存在，不能添加相同的标签");
//        }
//        prodTag.setIsDefault(0);
//        prodTag.setProdCount(0L);
//        prodTag.setCreateTime(new Date());
//        prodTag.setUpdateTime(new Date());
//        prodTag.setShopId(SecurityUtils.getSysUser().getShopId());
//        prodTagService.removeProdTag();
//        return ServerResponseEntity.success(prodTagService.save(prodTag));
//    }
//
//    /**
//     * 修改商品分组标签
//     *
//     * @param prodTag 商品分组标签
//     * @return 是否修改成功
//     */
//    @SysLog("修改商品分组标签")
//    @PutMapping
//    @PreAuthorize("@pms.hasPermission('prod:prodTag:update')")
//    public ServerResponseEntity<Boolean> updateById(@RequestBody @Valid ProdTag prodTag) {
//        prodTag.setUpdateTime(new Date());
//        prodTagService.removeProdTag();
//        return ServerResponseEntity.success(prodTagService.updateById(prodTag));
//    }
//
//    /**
//     * 通过id删除商品分组标签
//     *
//     * @param id id
//     * @return 是否删除成功
//     */
//    @SysLog("删除商品分组标签")
//    @DeleteMapping("/{id}")
//    @PreAuthorize("@pms.hasPermission('prod:prodTag:delete')")
//    public ServerResponseEntity<Boolean> removeById(@PathVariable Long id) {
//        ProdTag prodTag = prodTagService.getById(id);
//        if (prodTag.getIsDefault() != 0) {
//            throw new YamiShopBindException("默认标签不能删除");
//        }
//        prodTagService.removeProdTag();
//        return ServerResponseEntity.success(prodTagService.removeById(id));
//    }
//
//    @GetMapping("/listTagList")
//    public ServerResponseEntity<List<ProdTag>> listTagList() {
//        return ServerResponseEntity.success(prodTagService.listProdTag());
//
//    }


}
