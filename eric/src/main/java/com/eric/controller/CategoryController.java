package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.repository.entity.CategoryEntity;
import com.eric.repository.entity.Product;
import com.eric.service.CategoryService;
import com.eric.service.ProductService;
import com.eric.service.SmsCodeService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/category")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class CategoryController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
//    @Resource
//    private TokenManager mTokenManager;
    @Resource
    private CategoryService mService;


    /**
     * 分类信息列表接口
     */
    @RequestMapping(value = {"/categoryInfo"}, method = {RequestMethod.GET})
    @Operation(summary = "分类信息列表" , description = "获取所有的产品分类信息，顶级分类的parentId为0,默认为顶级分类")
    @Parameter(name = "parentId", description = "分类ID", required = false)
    public BaseResponse<List<CategoryEntity>> categoryInfo(@RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        BaseResponse<List<CategoryEntity>> responseEntity;
        try {
            logger.info("categoryInfo" + ",parentId:" + parentId);
            List<CategoryEntity> data = mService.listByParentId(parentId);
            responseEntity =  BaseResponse.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }


    /**
     * 获取信息
     */
    @RequestMapping(value = {"/info"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<CategoryEntity> info(Long prodId) {

        BaseResponse<CategoryEntity> responseEntity;
        try {
            logger.info("info" + ",prodId:" + prodId);
            CategoryEntity prod = mService.getItem(prodId);
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
    public BaseResponse<List<CategoryEntity>> selectAll(int pageNum,int pageSize) {

        BaseResponse<List<CategoryEntity>> responseEntity;
        try {
            logger.info("page" + ",pageNum:" + pageNum);
            PageHelper.startPage(pageNum,pageSize);
            List<CategoryEntity> list = mService.SelectAll();
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }

    /**
     * 获取信息
     */
    @RequestMapping(value = {"/listCategory"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<List<CategoryEntity>> listCategory() {

        BaseResponse<List<CategoryEntity>> responseEntity;
        try {
            List<CategoryEntity> list = mService.SelectAll();
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }

    /**
     * 首页产品分类
     */
    @RequestMapping(value = {"/range"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<List<CategoryEntity>> selectRange() {

        BaseResponse<List<CategoryEntity>> responseEntity;
        try {
            logger.info("selectRange" );
            List<CategoryEntity> list = mService.selectRange(10, 18);
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }
}
