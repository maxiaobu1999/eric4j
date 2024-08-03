package com.eric.repository;

import com.eric.repository.entity.Transfee;
import com.eric.repository.entity.TransfeeFree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface ITransfeeDao {
    /**
     * 插入运费金额
     * @param transfees
     */
    void insertTransfees(List<Transfee> transfees);

    /**
     * 插入包邮运费项
     * @param transfeeFrees
     */
    void insertTransfeeFrees(List<TransfeeFree> transfeeFrees);

    /**
     * 根据运费模板id删除
     * @param transportId
     */
    void deleteByTransportId(@Param("transportId") Long transportId);

    /**
     * 根据运费模板id删除包邮运费
     * @param transportId
     */
    void deleteTransfeeFreesByTransportId(@Param("transportId") Long transportId);

}
