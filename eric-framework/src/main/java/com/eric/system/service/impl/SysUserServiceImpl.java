package com.eric.system.service.impl;

import com.eric.core.domain.entity.SysUser;
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
    public int insertAccount(SysUser sysUser) {
        return mAccountDao.insertItem(sysUser);
    }

    @Override
    public SysUser queryByUserId(Long userId) {
        List<SysUser> accounts = mAccountDao.queryByUserId(userId);
        if (accounts != null && !accounts.isEmpty()) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    public SysUser findByUserName(String userName) {
        List<SysUser> accounts = mAccountDao.queryByUsername(userName);
        if (accounts != null && !accounts.isEmpty()) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    public ArrayList<SysUser> queryAllUser() {
        return mAccountDao.queryAllUser();
    }


    @Override
    public void updateByUserId(SysUser sysUser) {
        mAccountDao.updateByUserId(sysUser);
    }

    @Override
    public void deleteByUserId(Long userId) {
        mAccountDao.deleteByUserId(userId);
    }

    @Override
    public SysUser findAccountByPhoneNum(String phoneNum) {
        List<SysUser> accounts = mAccountDao.findByphoneNum(phoneNum);
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
