package com.eric.controller;

import com.eric.BaseResponse;
import com.eric.repository.dto.SearchProdDto;
import com.eric.repository.entity.Product;
import com.eric.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/search")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "搜索接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class SearchController  extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Resource
    private ProductService mProductService;


    @ApiOperation("分页排序搜索商品")
    @Operation(summary = "分页排序搜索商品", description = "根据商品名搜索")
    @Parameters({
            @Parameter(name = "prodName", description = "商品名" , required = true),
            @Parameter(name = "sort", description = "排序(0 默认排序 1销量排序 2价格排序)"),
            @Parameter(name = "orderBy", description = "排序(0升序 1降序)"),
            @Parameter(name = "shopId", description = "店铺id" , required = true),
    })
    @GetMapping("/searchProdPage")
    public BaseResponse<List<SearchProdDto>> searchProdPage(int current, int size, String prodName, Integer sort, Integer orderBy) {
        logger.info("searchProdPage");

        List<SearchProdDto> searchProdDtoPageByProdName = mProductService.getSearchProdDtoPageByProdName(current, size, prodName, sort, orderBy);
        return BaseResponse.success(searchProdDtoPageByProdName);
    }

}
