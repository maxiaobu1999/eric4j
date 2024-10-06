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
    List<ProdTag> list(ProdTag prodTag);

    public ProdTag getItem(Long id);
    public int removeById(Long id);
    public boolean save(ProdTag prodTag);
    public boolean updateById(ProdTag prodTag);

    /**
     * 删除商品分组标签缓存
     */
    void removeProdTag();
}
