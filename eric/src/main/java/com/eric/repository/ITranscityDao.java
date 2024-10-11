package com.eric.repository;

import com.eric.repository.entity.*;
import org.apache.ibatis.annotations.*;
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
     * 查询运费险相关城市
     * @param transfeeId 运费项id
     * @return 运费险相关城市
     */
    @Select("SELECT * FROM tz_transfee " +
            "WHERE transport_id = #{transportId}")
    List<Transcity> selectByTransfeeId(Long transfeeId);
    /**
     * 插入运费项中的城市
     * @param transcities
     */
    @Insert({
            "<script>",
            "INSERT INTO tz_transcity(transfee_id,city_id) ",
            "VALUES ",
            "<foreach collection='transcities' item='transcity' separator=',' >",
            "(",
            "#{transcity.transfeeId},#{transcity.cityId}",
            ")",
            "</foreach>",
            "</script>"
    })
    void insertTranscities(@Param("transcities") List<Transcity> transcities);

    /**
     * 插入运费
     * @param transcityFrees
     */
    @Insert({
            "<script>",
            "INSERT INTO tz_transcity_free(transfee_free_id,free_city_id) ",
            "VALUES ",
            "<foreach collection='transcityFrees' item='transcityFree' separator=',' >",
            "(",
            "#{transcityFree.transfeeFreeId},#{transcityFree.freeCityId}",
            ")",
            "</foreach>",
            "</script>"
    })
    void insertTranscityFrees(@Param("transcityFrees") List<TranscityFree> transcityFrees);

    /**
     * 根据运费id删除运费项
     * @param transfeeIds
     */
    @Delete({
            "<script>",
            "DELETE  FROM tz_transcity ",
            "WHERE transfee_id IN ",
            "( ",
            "<foreach collection='transfeeIds' item='transfeeId' OPEN='(' separator=',' close=')'>",
            "#{transfeeId}",
            "</foreach>",
            "</script>"
    })
    void deleteBatchByTransfeeIds(@Param("transfeeIds") List<Long> transfeeIds);

    /**
     * 根据运费金额项id删除运费金额
     * @param transfeeFreeIds
     */
    @Delete({
            "<script>",
            "DELETE  FROM tz_transcity_free ",
            "WHERE transfee_free_id IN ",
            "( ",
            "<foreach collection='transfeeFreeIds' item='transfeeFreeId' OPEN='(' separator=',' close=')'>",
            "#{transfeeFreeId}",
            "</foreach>",
            "</script>"
    })
    void deleteBatchByTransfeeFreeIds(@Param("transfeeFreeIds") List<Long> transfeeFreeIds);

}
