package com.eric.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    static SnowFlow snowFlow = new SnowFlow(1, 1, 1000);
    static SnowFlow snowFlowMsg = new SnowFlow(2, 1, 1000);

    public static long createID() {
        return snowFlow.nextId();
    }
    public static long createMessageID() {
        return snowFlowMsg.nextId();
    }
    /** 验证手机号码 */
    public static boolean isPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }
}
