
package com.eric.service.impl;


import com.eric.exception.BusinessException;
import com.eric.repository.IUserAddrDao;
import com.eric.repository.entity.UserAddr;
import com.eric.service.UserAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class UserAddrServiceImp implements UserAddrService {

	@Resource
	private IUserAddrDao mUserAddrDao;

	@Override
	public UserAddr getDefaultUserAddr(String userId) {
		return mUserAddrDao.getDefaultUserAddr(userId);
	}

	@Override
	public void updateDefaultUserAddr(Long addrId, String userId) {
		mUserAddrDao.removeDefaultUserAddr(userId);
		int setCount = mUserAddrDao.setDefaultUserAddr(addrId,userId);
//		if (setCount == 0) {
//			throw new Exception("无法修改用户默认地址，请稍后再试");
//		}
	}

	@Override
	@CacheEvict(cacheNames = "UserAddrDto", key = "#userId+':'+#addrId")
	public void removeUserAddrByUserId(Long addrId, String userId) {

	}

	@Override
//	@Cacheable(cacheNames = "UserAddrDto", key = "#userId+':'+#addrId")
	public UserAddr getUserAddrByUserId(Long addrId, String userId) {
		if (addrId == 0) {
			return mUserAddrDao.getDefaultUserAddr(userId);
		}
		return mUserAddrDao.getUserAddrByUserIdAndAddrId(userId, addrId);
	}

	@Override
	public List<UserAddr> list(String userId) {
		return mUserAddrDao.list(userId);
	}

	@Override
	public void save(UserAddr userAddr) {
		mUserAddrDao.insert(userAddr);
	}

	@Override
	public void updateById(UserAddr userAddr) {
		mUserAddrDao.updateById(userAddr);

	}

	@Override
	public void removeById(Long addrId) {
		mUserAddrDao.removeById(addrId);

	}

}

