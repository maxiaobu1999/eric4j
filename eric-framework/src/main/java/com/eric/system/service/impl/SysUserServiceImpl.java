package com.eric.system.service.impl;

import com.eric.core.domain.entity.UserEntity;
import com.eric.system.repository.ISysUserDao;
import com.eric.system.service.ISysUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author zhimin
 */
@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class SysUserServiceImpl implements ISysUserService
{
    @Resource
    private ISysUserDao mAccountDao;

    @Override
    public int insertAccount(UserEntity userEntity) {
        return mAccountDao.insertItem(userEntity);
    }

    @Override
    public UserEntity queryByUserId(Long userId) {
        List<UserEntity> accounts = mAccountDao.queryByUserId(userId);
        if (accounts != null && !accounts.isEmpty()) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    public UserEntity findByUserName(String userName) {
        List<UserEntity> accounts = mAccountDao.queryByUsername(userName);
        if (accounts != null && !accounts.isEmpty()) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    public ArrayList<UserEntity> queryAllUser() {
        return mAccountDao.queryAllUser();
    }


    @Override
    public void updateByUserId(UserEntity userEntity) {
        mAccountDao.updateByUserId(userEntity);
    }

    @Override
    public void deleteByUserId(Long userId) {
        mAccountDao.deleteByUserId(userId);
    }

    @Override
    public UserEntity findAccountByPhoneNum(String phoneNum) {
        List<UserEntity> accounts = mAccountDao.findByphoneNum(phoneNum);
        if (accounts != null && !accounts.isEmpty()) {
            return accounts.get(0);
        }
        return null;
    }

//    @Override
//    public void updateAccountByUserId(AccountInfo accountInfo) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(accountInfo.userId);
//        if (!StringUtils.isEmpty(accountInfo.getPhoneNum())) {
//            userEntity.setPhoneNum(accountInfo.getPhoneNum());
//        }
//        if (!StringUtils.isEmpty(accountInfo.getPassword())) {
//            userEntity.setPassword(accountInfo.getPassword());
//        }
//        if (!StringUtils.isEmpty(accountInfo.getUserName())) {
//            userEntity.setUserName(accountInfo.getUserName());
//        }
//        if (!StringUtils.isEmpty(accountInfo.getAvatar())) {
//            userEntity.setAvatar(accountInfo.getAvatar());
//        }
//        if (!StringUtils.isEmpty(accountInfo.getNickname())) {
//            userEntity.setNickname(accountInfo.getNickname());
//        }
//        mAccountDao.updateByUserId(userEntity);
//    }
}
