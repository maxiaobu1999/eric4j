package com.eric.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.entity.ProdTag;
import com.eric.service.ProdTagService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 商品分组
 *
 */
@RestController
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@RequestMapping("/prod/prodTag")
public class ProdTagController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProdTagService mProdTagService;

    /**
     * 分页查询
     *
     * @param prodTag 商品分组标签
     * @return 分页数据
     */
    @GetMapping("/page")
    public BaseResponse<List<ProdTag>> getProdTagPage(int pageNum, int pageSize, ProdTag prodTag) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProdTag> tagPage = mProdTagService.listProdTag();
        return BaseResponse.success(tagPage);

    }


    /**
     * 通过id查询商品分组标签
     *
     * @param id id
     * @return 单个数据
     */
    @GetMapping("/info/{id}")
    public BaseResponse<ProdTag> getById(@PathVariable("id") Long id) {
        log.info("info: id={}", id);
        BaseResponse<ProdTag> responseEntity;
        try {
            log.info("info" + ",prodId:" + id);
            ProdTag prod = mProdTagService.getItem(id);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(prod);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : " + e.getMessage());
        }
        return responseEntity;
    }

    /**
     * 新增商品分组标签
     *
     * @param prodTag 商品分组标签
     * @return 是否新增成功
     */
//    @SysLog("新增商品分组标签")
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('prod:prodTag:save')")
    public BaseResponse<Boolean> save(HttpServletRequest request,  @RequestBody @Valid ProdTag prodTag) {
        // 查看是否相同的标签
        List<ProdTag> list = mProdTagService.list(prodTag);
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));

        if (CollectionUtil.isNotEmpty(list)) {
            return BaseResponse.fail("标签名称已存在，不能添加相同的标签");
        }
        prodTag.setIsDefault(0);
        prodTag.setProdCount(0L);
        prodTag.setCreateTime(new Date());
        prodTag.setUpdateTime(new Date());
        prodTag.setShopId(1L);
        mProdTagService.removeProdTag();
        return BaseResponse.success(mProdTagService.save(prodTag));
    }

    /**
     * 修改商品分组标签
     *
     * @param prodTag 商品分组标签
     * @return 是否修改成功
     */
//    @SysLog("修改商品分组标签")
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('prod:prodTag:update')")
    public BaseResponse<Boolean> updateById(@RequestBody @Valid ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        mProdTagService.removeProdTag();
        return BaseResponse.success(mProdTagService.updateById(prodTag));
    }

    /**
     * 通过id删除商品分组标签
     *
     * @param id id
     * @return 是否删除成功
     */
//    @SysLog("删除商品分组标签")
    @DeleteMapping("/{id}")
//    @PreAuthorize("@pms.hasPermission('prod:prodTag:delete')")
    public BaseResponse<Boolean> removeById(@PathVariable Long id) {
        ProdTag prodTag = mProdTagService.getItem(id);
        if (prodTag.getIsDefault() != 0) {
            return BaseResponse.fail("默认标签不能删除");
        }
        mProdTagService.removeProdTag();
        mProdTagService.removeById(id);
        return BaseResponse.success();
    }

    @GetMapping("/listTagList")
    public BaseResponse<List<ProdTag>> listTagList() {
        log.info("listTagList:");
        BaseResponse<ProdTag> responseEntity;
        try {
            List<ProdTag> prodTags = mProdTagService.listProdTag();
            return  BaseResponse.success(prodTags);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.fail(e.getMessage());
        }
    }


}
