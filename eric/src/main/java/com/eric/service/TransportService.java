package com.eric.service;


import com.eric.repository.entity.Transport;

import java.util.List;

/**
 * @author lgh on 2018/11/16.
 */
public interface TransportService {
    /**
     * 插入运费模板和运费项
     *
     * @param transport
     */
    void insertTransportAndTransfee(Transport transport);

    /**
     * 根据运费模板和运费项
     *
     * @param transport
     */
    void updateTransportAndTransfee(Transport transport);

    /**
     * 根据id列表删除运费模板和运费项
     *
     * @param ids
     */
    void deleteTransportAndTransfeeAndTranscity(Long[] ids);

    /**
     * 根据模板id获取运费模板和运费项
     *
     * @param transportId
     * @return
     */
    Transport getTransportAndAllItems(Long transportId);

    /**
     * 删除缓存
     *
     * @param transportId
     */
    void removeTransportAndAllItemsCache(Long transportId);


    /**
     * 删除缓存
     *
     * @param transportId
     */
    List<Transport> list(Long transportId);

}
