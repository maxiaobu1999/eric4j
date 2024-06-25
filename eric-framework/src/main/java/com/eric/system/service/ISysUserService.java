package com.eric.system.service;


import com.eric.core.domain.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层
 *
 * @author zhimin
 */
public interface ISysUserService {
       /**
     * 创建账户
     *
     * @param sysUser 账户信息
     * @return 生成的userId
     */
    int insertAccount(SysUser sysUser);

    /**
     * 查找账户，userId
     */
    SysUser queryByUserId(Long userId);

    /**
     * 查找账户，userName
     */
    SysUser findByUserName(String userName);

    /**
     * @return 全部用户列表
     */
    ArrayList<SysUser> queryAllUser();


    /**
     * 更新账户，基于userId
     */
    void updateByUserId(SysUser sysUser);

    /**
     * 删除账户，基于userId
     */
    void deleteByUserId(Long userId);

    /** 查找账户，手机号 */
    SysUser findAccountByPhoneNum(String phoneNum);

//
//
//    /** 更新账户，基于userId */
//    void updateAccountByUserId(AccountInfo accountInfo);
}
