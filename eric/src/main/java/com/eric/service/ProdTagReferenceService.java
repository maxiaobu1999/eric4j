
package com.eric.service;


import java.util.List;

/**
 * 分组标签引用
 */
public interface ProdTagReferenceService{
    /**
     * 根据id获取标签id列表
     * @param prodId 商品ID
     * @return 分组id集合
     */
    List<Long> listTagIdByProdId(Long prodId);
}
