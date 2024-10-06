package com.eric.controller;


import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.redis.TokenManager;
import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.param.LoginParam;
import com.eric.service.SmsCodeService;
import com.eric.service.UserService;
import com.eric.utils.PasswordUtils;
import com.eric.utils.StringUtils;
import com.eric.utils.Util;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;

import static org.apache.logging.log4j.util.Strings.isEmpty;
@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/user")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    //
//    @Resource
//    private TokenManager mTokenManager;
    @Resource
    private SmsCodeService mSmsCodeService;
    @Resource
    private UserService mUserService;
    @Resource
    private TokenManager mTokenManager;


    /**
     * 账户登录
     * http://localhost:8089/user/login/username?username=1514311&password=1234
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/login/username"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<UserEntity> loginByUsername(String username, String password) {
        BaseResponse<UserEntity> responseEntity;
        try {
            logger.info("loginByUsername" + ",username:" + username);
            if (StringUtils.isEmpty(username)) {
                responseEntity = new BaseResponse<>(1, "用户名是null的");
                return responseEntity;
            }
            if (StringUtils.isEmpty(password)) {
                responseEntity = new BaseResponse<>(1, "密码是null的");
                return responseEntity;
            }
            UserEntity userEntity = mUserService.findByName(username);
            if (userEntity == null) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（用户名）");
                return responseEntity;
            }

            if (!PasswordUtils.matches(userEntity.getSalt(), password, userEntity.getLoginPassword())) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（密码）");
                return responseEntity;
            }

            // 查userId
            String userId = userEntity.getUserId().toString();
//            // 更新的token
//            String token = mTokenManager.generateToken(Long.parseLong(userId));
            // 生成 accessToken
            String accessToken = JwtUtils.accessSign(username, Long.valueOf(userId));

            responseEntity = new BaseResponse<>(0, "登录成功", accessToken);
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "登录失败，为啥不知道");
        }
        return responseEntity;
    }
    /**
     * 账户登录
     * http://localhost:8089/user/login
     *
     * @return token
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public BaseResponse<UserEntity> login(@RequestBody LoginParam param) {
        BaseResponse<UserEntity> responseEntity;
        try {
            logger.info("login" + ",param:" + param);
            if (StringUtils.isEmpty(param.getUsername())) {
                responseEntity = new BaseResponse<>(1, "用户名是null的");
                return responseEntity;
            }
            if (StringUtils.isEmpty(param.getPassword())) {
                responseEntity = new BaseResponse<>(1, "密码是null的");
                return responseEntity;
            }
            UserEntity userEntity = mUserService.findByName(param.getUsername());
            if (userEntity == null) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（用户名）");
                return responseEntity;
            }

            if (!PasswordUtils.matches(userEntity.getSalt(), param.getPassword(), userEntity.getLoginPassword())) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（密码）");
                return responseEntity;
            }

            // 查userId
            String userId = userEntity.getUserId().toString();
//            // 更新的token
//            String token = mTokenManager.generateToken(Long.parseLong(userId));
            // 生成 accessToken
            String accessToken = JwtUtils.accessSign(param.getUsername(), Long.valueOf(userId));

            responseEntity = new BaseResponse<>(0, "登录成功", accessToken);
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "登录失败，为啥不知道");
        }
        return responseEntity;
    }

    /**
     * 注册
     * http://localhost:8089/user/register/username?username=1514311&password=1234
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/register/username"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<UserEntity> registerByUsername(String username, String password) {
        BaseResponse<UserEntity> responseEntity;
        try {
            if (isEmpty(username)) {
                responseEntity = new BaseResponse<>(1, "用户名是null的");
                return responseEntity;
            }
            if (isEmpty(password)) {
                responseEntity = new BaseResponse<>(1, "密码是null的");
                return responseEntity;
            }
            UserEntity accounts = mUserService.findByName(username);
            if (accounts != null) {
                responseEntity = new BaseResponse<>(1, "用户名已存在");
                return responseEntity;
            }

            // 插入用户信息
            UserEntity userEntity = new UserEntity();
            userEntity.setRealName(username);
            userEntity.setNickName(username);
            userEntity.setModifyTime(new Date(System.currentTimeMillis()));
            userEntity.setUserRegtime(new Date(System.currentTimeMillis()));
            userEntity.setSalt(PasswordUtils.getSalt());
            userEntity.setLoginPassword(PasswordUtils.encode(password, userEntity.getSalt()));

            userEntity.setUserId(String.valueOf(Util.createID()));
            int res = mUserService.insertAccount(userEntity);
            logger.info("registerByUsername=="+res);

            // 返回数据
            responseEntity = new BaseResponse<>(0, "注册成功", String.valueOf(userEntity.getUserId()));
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "注册失败，为啥不知道");
        }
        return responseEntity;
    }

    /**
     * 查询用户信息
     * <p>
     * http://localhost:8088/user/info?token=308328080
     *
     * @return 用户信息
     */

    @RequestMapping(value = {"/info"}, method = {RequestMethod.POST,RequestMethod.GET})
    public BaseResponse<UserEntity> userInfo(HttpServletRequest request) {
        BaseResponse<UserEntity> responseEntity;
        try {
            String token = request.getHeader(Constant.ACCESS_TOKEN);
            logger.info("QueryUser#{\"url\":{}, \"method\":{}, \"token\":{}}", "/account/queryUser", RequestMethod.GET, token);
            // 查询用户信息
            Long userId = JwtUtils.getUserId(token);

            UserEntity accountEntity = mUserService.findByUserId(userId);
            responseEntity = new BaseResponse<>(0, "获取用户信息成功");
            responseEntity.setData(accountEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "获取用户信息，为啥不知道");
        }
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return responseEntity;
    }


//    /**
//     * 修改用户信息
//     * <p>
//     * <p>
//     * http://localhost:8088/account/updateUserInfo?token=1490600326&accountNew="{ \"userId\": \"\", \"phoneNum\": 0, \"userName\": \"1514311\", \"password\": \"123\", \"email\": null, \"avatar\": null, \"gender\": null, \"identifier\": null }"
//     *
//     * @param accountNew 用户信息的json
//     */
//    @RequestMapping(value = {"/updateUserInfo"}, method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public BaseResponse<String> updateUserInfo(@RequestBody UserEntity accountNew, @RequestParam int userId) {
//        BaseResponse<String> responseEntity;
//        try {
//            // 修改用户信息，但是userId不能变
//            accountNew.setUserId(userId);
//            mAccountService.updateAccountByUserId(accountNew);
//            responseEntity = new BaseResponse<>(0, "修改用户信息成功");
//            responseEntity.setData("");
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseEntity = new BaseResponse<>(-1, "修改用户信息失败，为啥不知道");
//        }
//        return responseEntity;
//    }

    /**
     * 获取验证码
     * http://localhost:8089/user/smsCode?phoneNum=15652814311
     *
     * @param phoneNum 手机号码
     */
//    @SkipTokenValidate
    @RequestMapping(value = {"/smsCode"}, method = {RequestMethod.GET})// 配置url映射,二级
    public BaseResponse<String> smsCode(@RequestParam String phoneNum) {
        BaseResponse<String> responseEntity;
        if (!Util.isPhone(phoneNum)) {
            responseEntity = new BaseResponse<>(-1, "手机号码填写错误");
            return responseEntity;
        }
        int smsCode = mSmsCodeService.getSmsCode(phoneNum);
        responseEntity = new BaseResponse<>(0,
                "短信收收费,验证码填：1234");
        responseEntity.setData(String.valueOf(smsCode));
        return responseEntity;
    }




    /**
     * 注销
     * http://localhost:8089/user/delete/userid?id=509434321728311296
     *
     * @param id 用户id
     * @return token
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/delete/userid"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<UserEntity> registerByUsername(String id) {
        BaseResponse<UserEntity> responseEntity;
        try {
            if (isEmpty(id)) {
                responseEntity = new BaseResponse<>(1, "userId是null的");
                return responseEntity;
            }
            mUserService.deleteByUserId(Long.valueOf(id));
            // 返回数据
            responseEntity = new BaseResponse<>(0, "注销成功");
        } catch (Exception e) {

            responseEntity = new BaseResponse<>(-1, e.toString());
        }
        return responseEntity;
    }




    /**
     * 修改密码
     * http://localhost:8089/user/modifyPassword/username?username=1514311&passwordOld=1234&passwordNew=123
     *
     * @param username    用户名
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @return token
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/modifyPassword/username"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<UserEntity> modifyPasswordByUsername(String username, String passwordOld, String passwordNew) {
        BaseResponse<UserEntity> responseEntity;
        try {
            if (StringUtils.isEmpty(username)) {
                responseEntity = new BaseResponse<>(1, "用户名是null的");
                return responseEntity;
            }
            if (StringUtils.isEmpty(passwordOld)) {
                responseEntity = new BaseResponse<>(1, "旧密码是null的");
                return responseEntity;
            }
            if (StringUtils.isEmpty(passwordNew)) {
                responseEntity = new BaseResponse<>(1, "新密码是null的");
                return responseEntity;
            }
            UserEntity userEntity = mUserService.findByName(username);
            if (userEntity == null) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（用户名null）");
                return responseEntity;
            }

            if (!passwordOld.equals(userEntity.getLoginPassword())) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（密码）");
                return responseEntity;
            }
            // 修改密码
            userEntity.setLoginPassword(passwordNew);
            mUserService.updateByUserId(userEntity);
            responseEntity = new BaseResponse<>(0, "修改密码成功");
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "修改密码失败，为啥不知道");
        }
        return responseEntity;
    }

    /**
     * 查询用户列表
     * http://localhost:8089/user/query/users
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/query/users"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<ArrayList<UserEntity>> queryAllUsers() {
        BaseResponse<ArrayList<UserEntity>> responseEntity;
        try {
            ArrayList<UserEntity> userEntities = mUserService.queryAllUser();
            responseEntity = new BaseResponse<>(0, "查询成功");
            responseEntity.setData(userEntities);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "修改密码失败，为啥不知道");
        }
        return responseEntity;
    }

    /**
     * 查询用户列表
     * http://localhost:8089/user/page
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/page"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<ArrayList<UserEntity>> usersPage(int pageNum, int pageSize) {
        BaseResponse<ArrayList<UserEntity>> responseEntity;
        logger.info("usersPage" + ",pageNum:" + pageNum);
        try {
            PageHelper.startPage(pageNum, pageSize);
            ArrayList<UserEntity> userEntities = mUserService.queryAllUser();
            responseEntity = new BaseResponse<>(0, "查询成功");
            responseEntity.setData(userEntities);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "修改密码失败，为啥不知道");
        }
        return responseEntity;
    }

    /**
     * 手机号码注册
     * <p>
     * http://localhost:8089/user/register/mobile?phoneNum=15652814311&code=1234
     *
     * @param phoneNum 手机号码
     * @param code     验证码
     * @return token
     */
    @RequestMapping(value = {"/register/mobile"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<UserEntity> mobileRegister(
            @RequestParam String phoneNum, @RequestParam String code) {
        logger.info("mobileRegister#{\"url\":{}, \"method\":{}}", "/account", RequestMethod.POST);
        BaseResponse<UserEntity> responseEntity;
        try {
            if (!Util.isPhone(phoneNum)) {
                responseEntity = new BaseResponse<>(1, "请输入正确手机号码");
                return responseEntity;
            }
            if (StringUtils.isEmpty(code)) {
                responseEntity = new BaseResponse<>(1, "没填验证码");
                return responseEntity;
            }
            if (!code.equals("1234")) {
                responseEntity = new BaseResponse<>(1, "验证码填写错误，填1234");
                return responseEntity;
            }
            UserEntity accounts = mUserService.findAccountByPhoneNum(phoneNum);
            if (accounts != null) {
                responseEntity = new BaseResponse<>(1, "手机号已存在");
                return responseEntity;
            }

            // 插入用户信息
            UserEntity userEntity = new UserEntity();
            userEntity.setUserMobile(phoneNum);
            userEntity.setUserId(String.valueOf(Util.createID()));
            userEntity.setNickName(userEntity.getUserId());
            mUserService.insertAccount(userEntity);

            // 返回数据
            responseEntity = new BaseResponse<>(0, "注册成功", String.valueOf(userEntity.getUserId()));
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "注册失败，为啥不知道");
        }
        return responseEntity;
    }


    /**
     * 手机登录
     * http://localhost:8089/user/login/mobile?phoneNum=15652814312&code=1234
     *
     * @param phoneNum 手机号
     * @param code     验证码
     * @return token
     */
    @RequestMapping(value = {"/login/mobile"}, method = {RequestMethod.POST, RequestMethod.GET})
    @SuppressWarnings("Duplicates")
    public BaseResponse<UserEntity> mobileLogin(@RequestParam String phoneNum, @RequestParam String code) {
        BaseResponse<UserEntity> responseEntity;
        try {
            if (!Util.isPhone(phoneNum)) {
                responseEntity = new BaseResponse<>(1, "请输入正确手机号码");
                return responseEntity;
            }
            if (StringUtils.isEmpty(code)) {
                responseEntity = new BaseResponse<>(1, "没填验证码");
                return responseEntity;
            }
            if (!code.equals("1234")) {
                responseEntity = new BaseResponse<>(1, "验证码填写错误，填1234");
                return responseEntity;
            }
            UserEntity userEntity = mUserService.findAccountByPhoneNum(phoneNum);

            if (userEntity == null) {
                responseEntity = new BaseResponse<>(1, "用户名不存在或密码错误（用户名）");
                return responseEntity;
            }

            // 查userId
            String userId = userEntity.getUserId().toString();
            // 更新的token
            responseEntity = new BaseResponse<>(0, "登录成功", String.valueOf(userId));
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "注册失败，为啥不知道");
        }
        return responseEntity;
    }

    /**
     * 修改密码
     * http://localhost:8089/user/modify/avatar?userId=509797888029757440&avatar=https://tse2-mm.cn.bing.net/th/id/OIP-C.dWr0cmLRjSSgKUMmphwTtAHaEc?rs=1&pid=ImgDetMain
     *
     * @param userId 用户id
     * @param avatar 头像
     * @return token
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = {"/modify/avatar"}, method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<UserEntity> modifyPasswordByUsername(String userId, String avatar) {
        BaseResponse<UserEntity> responseEntity;
        try {
            Long id = -1L;
            try {
                id = Long.parseLong(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (id == -1) {
                responseEntity = new BaseResponse<>(1, "userId是不合法");
                return responseEntity;
            }
            if (StringUtils.isEmpty(avatar)) {
                responseEntity = new BaseResponse<>(1, "avatar是null的");
                return responseEntity;
            }

            UserEntity userEntity = mUserService.findByUserId(id);
            if (userEntity == null) {
                responseEntity = new BaseResponse<>(1, "userId不存在");
                return responseEntity;
            }
            // 修改头像
            userEntity.setPic(avatar);
            mUserService.updateByUserId(userEntity);
            responseEntity = new BaseResponse<>(0, "修改头像成功");
            responseEntity.setData(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new BaseResponse<>(-1, "修改头像失败，为啥不知道");
        }
        return responseEntity;
    }





}
