
package com.eric.service;


import com.eric.repository.entity.UserAddr;

import java.util.List;

/**
 * @author lanhai
 */
public interface UserAddrService{
	/**
	 * 获取用户默认地址
	 * @param userId
	 * @return
	 */
	UserAddr getDefaultUserAddr(String userId);

	/**
	 * 更新默认地址
	 * @param addrId 默认地址id
	 * @param userId 用户id
	 */
	void updateDefaultUserAddr(Long addrId, String userId);

	/**
	 * 删除缓存
	 * @param addrId
	 * @param userId
	 */
    void removeUserAddrByUserId(Long addrId, String userId);

	/**
	 * 根据用户id和地址id获取用户地址
	 * @param addrId
	 * @param userId
	 * @return
	 */
    UserAddr getUserAddrByUserId(Long addrId, String userId);

	/**
	 * 根据用户id获取用户地址列表
	 * @param userId
	 * @return
	 */
	List<UserAddr> list(String userId);


	void save(UserAddr userAddr);
	void updateById(UserAddr userAddr);
	void removeById(Long addrId);
}

