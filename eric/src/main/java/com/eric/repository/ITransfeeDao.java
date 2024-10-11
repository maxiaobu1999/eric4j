package com.eric.repository;

import com.eric.repository.entity.Transfee;
import com.eric.repository.entity.TransfeeFree;
import com.eric.repository.entity.Transport;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运费项
 */
@Repository
public interface ITransfeeDao {
    @Select("SELECT * FROM tz_transfee " +
            "WHERE transport_id = #{transportId}")
    List<Transfee> selectByTransportId(Long transportId);
    /**
     * 插入运费金额
     * @param transfees
     */
    @Insert({
            "<script>",
            "INSERT INTO tz_transfee(transport_id,continuous_piece,first_piece,continuous_fee,first_fee) ",
            "VALUES ",
            "<foreach collection='list' item='transfee' separator=',' >",
            "(",
            "#{transfee.transportId},#{transfee.continuousPiece},#{transfee.firstPiece},#{transfee.continuousFee},#{transfee.firstFee}",
            ")",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "transfeeId", keyColumn = "transfee_id")//单条插入返回主键
    void insertTransfees(List<Transfee> transfees);

    /**
     * 插入包邮运费项
     * @param transfeeFrees
     */
    @Insert({
            "<script>",
            "INSERT INTO tz_transfee_free(free_type,amount,piece,transport_id) ",
            "VALUES ",
            "<foreach collection='list' item='transfeeFree' separator=',' >",
            "(",
            "#{transfeeFree.freeType},#{transfeeFree.amount},#{transfeeFree.piece},#{transfeeFree.transportId}",
            ")",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "transfeeFreeId", keyColumn = "transfee_free_id")//单条插入返回主键
    void insertTransfeeFrees(List<TransfeeFree> transfeeFrees);

    /**
     * 根据运费模板id删除
     * @param transportId
     */
    @Delete("DELETE FROM  tz_transfee where transport_id=#{transportId} ")
    void deleteByTransportId(@Param("transportId") Long transportId);

    /**
     * 根据运费模板id删除包邮运费
     * @param transportId
     */
    @Delete("DELETE FROM  tz_transfee_free where transport_id=#{transportId} ")
    void deleteTransfeeFreesByTransportId(@Param("transportId") Long transportId);

}
