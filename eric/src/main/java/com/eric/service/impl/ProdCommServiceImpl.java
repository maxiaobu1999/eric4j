package com.eric.service.impl;

import com.eric.repository.IProdCommDao;
import com.eric.repository.dto.ProdCommDataDto;
import com.eric.repository.dto.ProdCommDto;
import com.eric.repository.entity.ProdComm;
import com.eric.service.ProdCommService;
import com.eric.utils.Arith;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ProdCommServiceImpl implements ProdCommService {
    @Resource
    private IProdCommDao mProdCommDao;

    @Override
    public ProdCommDataDto getProdCommDataByProdId(Long prodId) {
        ProdCommDataDto prodCommDataDto = mProdCommDao.getProdCommDataByProdId(prodId);
        //计算出好评率
        if (prodCommDataDto.getPraiseNumber() == 0 || prodCommDataDto.getNumber() == 0) {
            prodCommDataDto.setPositiveRating(0.0);
        } else {
            prodCommDataDto.setPositiveRating(Arith.mul(Arith.div(prodCommDataDto.getPraiseNumber(), prodCommDataDto.getNumber()), 100));
        }
        return prodCommDataDto;
    }

    @Override
    public List<ProdCommDto> getProdCommDtoPageByUserId(String userId) {
        return mProdCommDao.getProdCommDtoPageByUserId(userId);
    }

    @Override
    public List<ProdCommDto> getProdCommDtoPageByProdId(Long prodId, Integer evaluate) {
        List<ProdCommDto> prodCommDtos = mProdCommDao.getProdCommDtoPageByProdId(prodId, evaluate);
        for (ProdCommDto prodCommDto : prodCommDtos) {
            // 匿名评价
            if (prodCommDto.getIsAnonymous() == 1) {
                prodCommDto.setNickName(null);
            }
        }
        return prodCommDtos;
    }

    @Override
    public List<ProdComm> getProdCommPage(ProdComm prodComm) {
        return mProdCommDao.getProdCommPage(prodComm);

    }

    @Override
    public Integer save(ProdComm prodComm) {
        return mProdCommDao.save(prodComm);
    }

    @Override
    public Integer removeById(Long prodCommId) {
        return mProdCommDao.removeById(prodCommId);
    }
}
