package com.eric.controller;

import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.HotSearchDto;
import com.eric.repository.dto.ProdCommDataDto;
import com.eric.repository.dto.ProdCommDto;
import com.eric.repository.dto.SearchProdDto;
import com.eric.repository.entity.ProdComm;
import com.eric.repository.param.ProdCommParam;
import com.eric.service.ProdCommService;
import com.eric.service.ProductService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/prodComm")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "评论接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class ProdCommentController {
    private static final Logger logger = LoggerFactory.getLogger(ProdCommentController.class);
    @Resource
    private ProdCommService mProdCommService;

    @GetMapping("/prodCommData")
    @Operation(summary = "返回商品评论数据(好评率 好评数量 中评数 差评数)" , description = "根据商品id获取")
    public BaseResponse<ProdCommDataDto> getProdCommData(Long prodId) {
        logger.info("getProdCommData");
        ProdCommDataDto list = mProdCommService.getProdCommDataByProdId(prodId);
        return BaseResponse.success(list);
    }


    @Operation(summary = "根据用户返回评论分页数据" , description = "传入页码")
    @GetMapping("/prodCommPageByUser")
    public BaseResponse<List<ProdCommDto>> getProdCommPage(HttpServletRequest request, int pageNum, int pageSize) {
        logger.info("getProdCommPage");
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        PageHelper.startPage(pageNum, pageSize);
        List<ProdCommDto> list = mProdCommService.getProdCommDtoPageByUserId(userId);
        return BaseResponse.success(list);
    }
    @GetMapping("/prodCommPageByProd")
    @Operation(summary = "根据商品返回评论分页数据" , description = "传入商品id和页码")
    @Parameters({
            @Parameter(name = "prodId", description = "商品id" , required = true),
            @Parameter(name = "evaluate", description = "-1或null 全部，0好评 1中评 2差评 3有图" , required = true),
    })
    public BaseResponse<List<ProdCommDto>> getProdCommPageByProdId(HttpServletRequest request, int pageNum, int pageSize, Long prodId, Integer evaluate) {
        logger.info("getProdCommPageByProdId");
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        if (pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<ProdCommDto> list = mProdCommService.getProdCommDtoPageByProdId(prodId, evaluate);
        return BaseResponse.success(list);
    }


    @PostMapping
    @Operation(summary = "添加评论")
    public BaseResponse<ProdCommDataDto> saveProdCommPage(HttpServletRequest request, ProdCommParam prodCommParam) {
        logger.info("saveProdCommPage");
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));

        ProdComm prodComm = new ProdComm();
        prodComm.setProdId(prodCommParam.getProdId());
        prodComm.setOrderItemId(prodCommParam.getOrderItemId());
        prodComm.setUserId(userId);
        prodComm.setScore(prodCommParam.getScore());
        prodComm.setContent(prodCommParam.getContent());
        prodComm.setPics(prodCommParam.getPics());
        prodComm.setIsAnonymous(prodCommParam.getIsAnonymous());
        prodComm.setRecTime(new Date());
        prodComm.setStatus(0);
        prodComm.setEvaluate(prodCommParam.getEvaluate());
        mProdCommService.save(prodComm);
        return BaseResponse.success();
    }
    @DeleteMapping
    @Operation(summary = "删除评论" , description = "根据id删除")
    public BaseResponse<ProdCommDataDto> deleteProdComm(HttpServletRequest request,Long prodCommId) {
        logger.info("saveProdCommPage");
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        mProdCommService.removeById(prodCommId);
        return BaseResponse.success();
    }

}
