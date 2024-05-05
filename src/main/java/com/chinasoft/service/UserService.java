package com.chinasoft.service;


import com.chinasoft.repository.entity.UserEntity;

import java.util.ArrayList;

public interface UserService {

    /**
     * 创建账户
     *
     * @param userEntity 账户信息
     * @return 生成的userId
     */
    int insertAccount(UserEntity userEntity);

    /**
     * 查找账户，userId
     */
    UserEntity queryByUserId(Long userId);

    /**
     * 查找账户，userName
     */
    UserEntity findByUserName(String userName);

    /**
     * @return 全部用户列表
     */
    ArrayList<UserEntity> queryAllUser();


    /**
     * 更新账户，基于userId
     */
    void updateByUserId(UserEntity userEntity);

    /**
     * 删除账户，基于userId
     */
    void deleteByUserId(Long userId);

    /** 查找账户，手机号 */
    UserEntity findAccountByPhoneNum(String phoneNum);

//
//
//    /** 更新账户，基于userId */
//    void updateAccountByUserId(AccountInfo accountInfo);

}
