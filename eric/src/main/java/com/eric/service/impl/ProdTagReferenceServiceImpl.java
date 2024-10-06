
package com.eric.service.impl;


import com.eric.repository.IProdTagReferenceDao;
import com.eric.service.ProdTagReferenceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分组标签引用
 */
@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ProdTagReferenceServiceImpl  implements ProdTagReferenceService {

    @Resource
    private IProdTagReferenceDao mProdTagReferenceDao;

    @Override
    public List<Long> listTagIdByProdId(Long prodId) {
        return mProdTagReferenceDao.listTagIdByProdId(prodId);
    }
}
