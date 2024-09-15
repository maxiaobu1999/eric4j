package com.eric.controller;

import com.eric.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/upload")// 配置url映射,一级
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Value("${spring.profiles.active}")
    private String env;

    @PostMapping("/test")
    public BaseResponse<String> upload(@RequestParam(value = "file") MultipartFile file, String password) {
        BaseResponse<String> responseEntity;
        try {
            logger.info(env + "=--=uploadAvatar 文件类型是：");
            logger.info("上传" + ",file:" + file.getName());
            logger.info("上传" + ",file.getSize():" + file.getSize());
            logger.info("上传" + ",file.getOriginalFilename():" + file.getOriginalFilename());
            logger.info("上传" + ",file.getContentType():" + file.getContentType());
            logger.info("上传" + ",password:" + password);
            String filePath;
            if ("-dev".equals(env)) {
                filePath = "D:/sources/test";
            } else {
                filePath = "/home/ecs-assist-user/eric/sources/test/";
            }
            String fileName = file.getOriginalFilename();
            //获取文件
            File uploadParentFile = new File(filePath);
            //判断文件目录是否存在
            if (!uploadParentFile.exists()) {
                //如果不存在就创建文件夹
                uploadParentFile.mkdirs();
            }
            File uploadFile = new File(filePath + "/" + fileName);
            //将临时文件转存到指定磁盘位置
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseEntity = new BaseResponse<>(0, "http://10.88.27.192:8089/source/test/" + uploadFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "失败，为啥不知道");
        }
        return responseEntity;
    }

    // http://10.88.27.192:8089/source/avatar/
    @PostMapping("/avatar")
    public BaseResponse<String> uploadAvatar(@RequestPart MultipartFile file, @RequestParam String type) {
        BaseResponse<String> responseEntity;
        try {
            logger.info("uploadAvatar 文件类型是：" + type);
            String filePath;
            if ("-dev".equals(env)) {
                filePath = "D:/sources/avatar";
            } else {
                filePath = "/home/ecs-assist-user/eric/sources/avatar/";
            }
            //获取文件
            File parentDirectory = new File(filePath);
            //判断文件目录是否存在
            if (!parentDirectory.exists()) {
                //如果不存在就创建文件夹
                parentDirectory.mkdirs();
            }
            //定义一个文件唯一标识码（UUID）
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + type;
            File uploadFile = new File(filePath + "/" + fileName);
            //将临时文件转存到指定磁盘位置
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseEntity = new BaseResponse<>(0, uploadFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "失败，为啥不知道");
        }
        return responseEntity;
    }

    @PostMapping("/avatar/filename")
    public BaseResponse<String> uploadAvatar(@RequestPart MultipartFile file, @RequestParam String type, @RequestParam String fileName) {


        BaseResponse<String> responseEntity;
        try {
            logger.info("uploadAvatar 文件类型是：" + type);
            String filePath;
            if ("dev".equals(env)) {
                filePath = "D:/backend/sources/eric4j/";
            } else {
                filePath = "/home/ecs-assist-user/eric/sources/avatar/";
            }
            //获取文件
            File parentDirectory = new File(filePath);
            //判断文件目录是否存在
            if (!parentDirectory.exists()) {
                //如果不存在就创建文件夹
                parentDirectory.mkdirs();
            }
            File uploadFile = new File(filePath + "/" + fileName);
            //将临时文件转存到指定磁盘位置
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseEntity = new BaseResponse<>(0, uploadFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "失败，为啥不知道");
        }
        return responseEntity;
    }
}
