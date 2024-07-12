package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.repository.entity.AdvertisementEntity;
import com.eric.repository.entity.Product;
import com.eric.service.AdvertisementService;
import com.eric.service.ProductService;
import com.eric.service.SmsCodeService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class CommonController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    //
//    @Resource
//    private TokenManager mTokenManager;
    @Resource
    private AdvertisementService mAdvertisementService;



    /**
     * 获取信息
     */
    @RequestMapping(value = {"/advertisement"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<List<AdvertisementEntity>> advertisement() {

        BaseResponse<List<AdvertisementEntity>> responseEntity;
        try {
            logger.info("advertisement");
            List<AdvertisementEntity> list = mAdvertisementService.selectAll();
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }
}
