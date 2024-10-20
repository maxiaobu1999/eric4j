package com.eric.service;

import com.eric.repository.dto.ProdCommDataDto;
import com.eric.repository.dto.ProdCommDto;
import com.eric.repository.entity.ProdComm;

import java.util.List;

/**
 * 商品评论
 *
 */
public interface ProdCommService {
    /**
     * 根据商品id获取商品评论信息
     * @param prodId
     * @return
     */
    ProdCommDataDto getProdCommDataByProdId(Long prodId);

    /**
     * 根据用户id分页获取商品评论
     * @param userId
     * @return
     */
    List<ProdCommDto> getProdCommDtoPageByUserId(String userId);

    /**
     * 根据商品id和评价等级获取商品评价
     * @param prodId
     * @param evaluate
     * @return
     */
    List<ProdCommDto> getProdCommDtoPageByProdId( Long prodId, Integer evaluate);

    /**
     * 根据参数分页获取商品评价
     * @param prodComm
     * @return
     */
    List<ProdComm> getProdCommPage(ProdComm prodComm);


    /**
     * 添加评论
     * @param prodComm
     * @return
     */
    Integer save(ProdComm prodComm );

    /**
     * 删除评论
     * @param prodCommId
     * @return
     */
    Integer removeById(Long prodCommId );
}
