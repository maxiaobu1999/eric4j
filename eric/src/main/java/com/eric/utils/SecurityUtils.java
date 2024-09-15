package com.eric.utils;

import com.eric.constant.Constant;
import com.eric.core.domain.entity.UserEntity;
import com.eric.jwt.JwtUtils;
import lombok.experimental.UtilityClass;

/**
 * @author LGH
 */
@UtilityClass
public class SecurityUtils {

    private static final String USER_REQUEST = "/p/";

    /**
     * 获取用户
     */
    public UserEntity getUser() {

        String token = HttpContextUtils.getHttpServletRequest().getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));

        UserEntity userser = new UserEntity();
        userser.setUserId(userId);
        return userser;
    }
}
