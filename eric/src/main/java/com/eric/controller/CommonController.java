package com.eric.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eric.BaseResponse;
import com.eric.repository.entity.AdvertisementEntity;
import com.eric.repository.entity.NoticeEntity;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.WeatherEntity;
import com.eric.service.AdvertisementService;
import com.eric.service.ProductService;
import com.eric.service.SmsCodeService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

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


    /**
     * 天气
     */
    @RequestMapping(value = {"/weather"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<WeatherEntity> weather() {

        BaseResponse<WeatherEntity> responseEntity;
        try {
            logger.info("weather");
            String res = "{" +
                    "\"id\": 4004," +
                    "\"cityCode\": \"330503\"," +
                    "\"weatherInformation\": \"{\"province\":\"浙江\",\"city\":\"南浔区\",\"adcode\":\"330503\",\"temperature_float\":\"31.0\",\"humidity_float\":\"78.0\",\"windpower\":\"≤3\",\"weather\":\"多云\",\"temperature\":\"31\",\"humidity\":\"78\",\"reporttime\":\"2024-07-15 20:35:56\",\"winddirection\":\"东南\"}\"," +
                    "\"createTime\": \"2024-07-15 21:00:00\"," +
                    "\"icon\": \"/晴_20240204140333A136.png\"" +
                    "    }";
            WeatherEntity data = new WeatherEntity();
            data.temperature = "37";
            data.icon = "/20240204140333A136.png";
            data.weather = "晴";

            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }

    /**
     * 公告走马灯
     */
    @RequestMapping(value = {"/notice/list"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<NoticeEntity> notice() {
        BaseResponse<NoticeEntity> responseEntity;
        try {
            logger.info("/notice/list");
            String res = "{\n" +
                    "                \"createBy\": \"gujianyong\",\n" +
                    "                \"createTime\": \"2024-01-02 16:06:16\",\n" +
                    "                \"updateBy\": \"\",\n" +
                    "                \"updateTime\": null,\n" +
                    "                \"remark\": null,\n" +
                    "                \"noticeId\": 6,\n" +
                    "                \"tenantId\": 171,\n" +
                    "                \"tenantName\": null,\n" +
                    "                \"publish\": \"顾健勇\",\n" +
                    "                \"publishTime\": \"2024-01-02 16:05:16\",\n" +
                    "                \"activeTime\": \"2024-01-02 16:05:16\",\n" +
                    "                \"expireTime\": \"2024-02-07 17:05:16\",\n" +
                    "                \"noticeTitle\": \"亲爱的游客们，热烈欢迎踏入南浔古镇这片充满历史韵味的土地！\",\n" +
                    "                \"noticeType\": \"1\",\n" +
                    "                \"noticeContent\": null,\n" +
                    "                \"content\": \"[{\\\"title\\\":\\\"\\\",\\\"content\\\":\\\"亲爱的游客们，热烈欢迎踏入南浔古镇这片充满历史韵味的土地！我们诚挚地邀请您在这里漫步古街巷陌，感受江南水乡的独特风情，领略千年文化的深厚底蕴。古镇内设有丰富的旅游服务和设施，以确保您的旅程愉快而舒适。如有任何疑问或需求，请随时联系我们的工作人员。期待您在南浔古镇留下难忘的回忆，祝您旅途愉快！\\\",\\\"image\\\":\\\"\\\",\\\"sort\\\":1}]\",\n" +
                    "                \"status\": \"0\",\n" +
                    "                \"contentList\": null\n" +
                    "            }";
            NoticeEntity data = new NoticeEntity();
            data.setNoticeId("6");
            data.noticeTitle = "亲爱的游客们，热烈欢迎踏入南浔古镇这片充满历史韵味的土地！";
            String content = "[{\"title\":\"\",\"content\":\"亲爱的游客们，热烈欢迎踏入南浔古镇这片充满历史韵味的土地！我们诚挚地邀请您在这里漫步古街巷陌，感受江南水乡的独特风情，领略千年文化的深厚底蕴。古镇内设有丰富的旅游服务和设施，以确保您的旅程愉快而舒适。如有任何疑问或需求，请随时联系我们的工作人员。期待您在南浔古镇留下难忘的回忆，祝您旅途愉快！\",\"image\":\"\",\"sort\":1}]";
            data.content = StringEscapeUtils.unescapeJava(content);
                    logger.info("/notice/list : content = "+ data.content);

            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }
}
