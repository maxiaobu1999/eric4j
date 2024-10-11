package com.eric.service.impl;

import com.eric.repository.ISysUserDao;
import com.eric.repository.ITransportDao;
import com.eric.repository.entity.SysUser;
import com.eric.service.SysUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private ISysUserDao mSysUserDao;

    @Override
    public void updatePasswordByUserId(Long userId, String newPassword) {

    }

    @Override
    public void saveUserAndUserRole(SysUser user) {

    }

    @Override
    public void updateUserAndUserRole(SysUser user) {

    }

    @Override
    public void deleteBatch(Long[] userIds, Long shopId) {

    }

    @Override
    public SysUser getByUserName(String username) {
        return null;
    }

    @Override
    public SysUser getSysUserById(Long userId) {
        return null;
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return Collections.emptyList();
    }

    @Override
    public long getShopId(Long userId) {
        return mSysUserDao.selectShopId(userId);
    }
}
