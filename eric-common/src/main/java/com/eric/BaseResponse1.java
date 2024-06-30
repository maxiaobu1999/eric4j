package com.eric;


import com.eric.exception.ResponseInterface;

public enum BaseResponse1 implements ResponseInterface {

    /**
     * 这个要和前段约定好
     * code=0：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     * code=4010001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     * code=4010002：(凭证过期) 客户端请求 刷新凭证接口
     * code=4030001：没有权限禁止访问
     * code=400xxxx：系统主动抛出的业务异常
     * code=5000001：系统异常
     */
    SUCCESS(0, "操作成功"),
    NO_BIND_CARD(9, "没有绑定预付卡"),
    SYSTEM_ERROR(5000001, "系统异常请稍后再试"),
    DATA_ERROR(4000001, "传入数据异常"),
    METHOD_IDENTITY_ERROR(4000002, "数据校验异常"),
    METHOD_MER_ORDER_NO_ERROR(40000021, "数据校验异常 MerOrderNo 为空"),
    METHOD_ENROL_ID_ERROR(40000022, "数据校验异常 EnrolId 为空"),
    METHOD_QR_CODE_ERROR(40000023, "数据校验异常 支付二维码无效"),
    METHOD_NO_PRE_CARD_ERROR(40000024, "数据校验异常,您名下无已激活的预付卡！"),
    METHOD_NO_CARD_TYPE_ERROR(40000025, "数据校验异常,卡类型不能为空"),
    METHOD_NO_CARD_NO_ERROR(40000026, "数据校验异常,卡号不能为空"),
    METHOD_NO_CARD_CVN_ERROR(40000027, "数据校验异常,卡CVN不能为空"),
    METHOD_NO_CARD_PHONE_ERROR(40000028, "数据校验异常,卡预留手机号不能为空"),
    METHOD_NO_CARD_PHONE_CODE_ERROR(40000029, "数据校验异常,卡预留手机区号不能为空"),
    METHOD_NO_CARD_IN_DATE_ERROR(40000030, "数据校验异常,卡有效期不能为空"),
    METHOD_BIND_CARD_ERROR(40000031, "用户已绑定预付卡"),
    METHOD_CARD_BIND_ERROR(40000032, "此预付卡已被绑定"),
    METHOD_BANK_CARD_BIND_ERROR(40000033, "此银行卡已被绑定"),
    METHOD_BAND_CARD_ERROR(40000034, "用户已绑定银行卡"),
    ACCOUNT_ERROR(4000003, "该账号不存在"),
    ACCOUNT_PASSWORD_ERROR(4000004, "用户名密码不匹配"),
    OPERATION_ERROR(4000005, "操作失败"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(4000006, "操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(4000007, "操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(4000008, "操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(4000009, "菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(4000010, "菜单权限的标识符不能为空"),
    OPERATION_MENU_PERMISSION_URL_METHOD_NULL(4000011, "菜单权限的请求方式不能为空"),
    OPERATION_MENU_PERMISSION_URL_CODE_NULL(4000012, "菜单权限的按钮标识不能为空"),
    PHONE_INVALID(4000013, "无效的手机号码"),
    VERIFY_CODE_INVALID(4000014, "验证码无效"),
    USER_EXISTS(4000015, "用户已注册"),
    CONFIRM_PASSWORD_ERROR(4000016, "两次设置密码不一致"),
    DEVICE_ERROR(4000017, "已在其他设备登录"),
    DEVICE_REPEAT(4000018, "设备信息重复"),
    EXISTS_LOGIN_PASSWORD(4000019, "密码已设置"),
    PHONENUMBER_EXISTS(4000020, "手机号已注册"),
    PAY_PASSWORD_INVALID(4000021, "支付密码只能是数字"),
    LOGIN_PASSWORD_INVALID(4000022, "登录密码只能是8~12字母或数字"),
    PAY_PASSWORD_ERROR(4000023, "支付密码错误，请重新输入"),
    LOGIN_PASSWORD_ERROR(4000024, "密码错误，请重新输入"),
    ACCOUNT_LOCK(4010001, "该账号被锁定,请联系系统管理员"),
    TOKEN_CHANGE_DEVICE(4011001, "您的账号于{0}在另外一台终端设备登录。您的终端将下线，如需上线请重新登录。"),
    TOKEN_ERROR(4011002, "用户未登录，请重新登录"),
    TOKEN_NOT_NULL(4010001, "token 不能为空"),
    SHIRO_AUTHENTICATION_ERROR(4010001, "用户认证异常"),
    ACCOUNT_HAS_DELETED_ERROR(4010001, "该账号已被删除，请联系系统管理员"),
    TOKEN_PAST_DUE(4010002, "token失效,请刷新token"),
    REFRESH_TOKEN_PAST_DUE(4010002, "refreshToken失效,请重新登录"),
    ADMIN_REFRESH_TOKEN_PAST_DUE(4010003, "后台管理refreshToken失效,请重新登录"),
    ACCOUNT_LOCK_TIP(4010012, "该账号被锁定,请联系系统管理员"),
    OPERATION_MENU_PERMISSION_UPDATE(4010013, "操作的菜单权限存在子集关联不允许变更"),
    ROLE_PERMISSION_RELATION(4010014, "该菜单权限存在子集关联，不允许删除"),
    NOT_PERMISSION_DELETED_DEPT(4010015, "该组织机构下还关联着用户，不允许删除"),
    NOT_PERMISSION_DELETED_CONFIG(4010017, "该配置项下还关联着明细，不允许删除"),
    NOT_PERMISSION_ADD_CONFIG(4010018, "该配置项编码已存在，不允许再次创建"),
    NOT_PERMISSION(4030001, "没有权限访问该资源"),
    OLD_PASSWORD_ERROR(4010016, "旧密码不匹配"),
    NONE_PREPAY(4100001, "暂无预付卡"),
    PREPAY_APPLY(4100002, "预付卡审核中"),
    PREPAY_OPEN(4100003, "审核通过，去绑卡"),
    NONE_INFORMATION(4101001, "暂无数据"),
    NONE_BANK_CARD(4200001, "无效银行卡Id"),
    CARD_OCR_ERROR(4210001, "{0}"),
    START_END_NOTNULL(4300001, "开始时间和结束时间不能为空"),
    UP_SERVICE_CLOSE(4040001, "服务已关闭，请稍后重试");

    BaseResponse1(int messageCode, String message) {
        this.code = messageCode;
        this.msg = message;
    }

    /**
     * 响应码
     */
    private int code;

    /**
     * 提示
     */
    private String msg;

    @Override
    public int messageCode() {
        return code;
    }

    @Override
    public String message() {
        return msg;
    }
}
