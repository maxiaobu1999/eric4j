package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.SysMenu;
import com.eric.service.ProductService;
import com.eric.service.SmsCodeService;
import com.eric.service.SysMenuService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping("/sys/menu")
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class SysMenuController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    @Resource
    private SysMenuService mService;

    /**
     * 获取信息
     */
    @RequestMapping(value = {"/nav"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    @Operation(summary = "获取用户所拥有的菜单和权限" , description = "通过登陆用户的userId获取用户所拥有的菜单和权限")
    public BaseResponse<List<SysMenu>> nav(String userId) {
        BaseResponse<List<SysMenu>> responseEntity;
        try {
            logger.info("nav :" + ",userId = " + userId);
            List<SysMenu> menuList = mService.listMenuByUserId(Long.parseLong(userId));
            responseEntity = new BaseResponse<>(0, "成功");
            responseEntity.setData(menuList);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "error : "+e.getMessage());
        }
        return responseEntity;
    }

}
