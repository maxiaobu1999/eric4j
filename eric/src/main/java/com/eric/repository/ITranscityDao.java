package com.eric.repository;

import com.eric.repository.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.eric.repository.entity.TranscityFree;
import com.eric.repository.entity.TransfeeFree;import com.eric.repository.entity.Transfee;
import com.eric.repository.entity.Transport;
import com.eric.repository.entity.TransfeeFree;
/**
 *
 */
@Repository
public interface ITranscityDao {
    /**
     * 插入运费项中的城市
     * @param transcities
     */
    void insertTranscities(@Param("transcities") List<Transcity> transcities);

    /**
     * 插入运费
     * @param transcityFrees
     */
    void insertTranscityFrees(@Param("transcityFrees") List<TranscityFree> transcityFrees);

    /**
     * 根据运费id删除运费项
     * @param transfeeIds
     */
    void deleteBatchByTransfeeIds(@Param("transfeeIds") List<Long> transfeeIds);

    /**
     * 根据运费金额项id删除运费金额
     * @param transfeeFreeIds
     */
    void deleteBatchByTransfeeFreeIds(@Param("transfeeFreeIds") List<Long> transfeeFreeIds);

}
