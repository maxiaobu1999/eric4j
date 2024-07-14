package com.eric.service;

import com.eric.repository.entity.ProdTag;

import java.util.List;

/**
 * 商品分组标签
 *
 * @author hzm
 * @date 2019-04-18 10:48:44
 */
public interface ProdTagService {
    /**
     * 获取商品分组标签列表
     *
     * @return
     */
    List<ProdTag> listProdTag();

    public ProdTag getItem(Long prodId);

    /**
     * 删除商品分组标签缓存
     */
    void removeProdTag();
}
