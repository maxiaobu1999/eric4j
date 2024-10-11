package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.dto.MyOrderItemDto;
import com.eric.repository.entity.Area;
import com.eric.repository.entity.Sku;
import com.eric.repository.entity.TransfeeFree;
import com.eric.repository.entity.Transport;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户信息
 */
@Repository
public interface ITransportDao {

    @Insert("INSERT INTO tz_transport(trans_name,create_time,shop_id,charge_type,is_free_fee,has_free_condition)" +
            "VALUES(#{transName},NOW(),#{shopId},#{chargeType}" + ",#{isFreeFee}" + ",#{hasFreeCondition})")
    @Options(useGeneratedKeys = true, keyProperty = "transportId", keyColumn = "transport_id")//单条插入返回主键
    Long insert(Transport item);

    @Update("UPDATE tz_transport SET " +
            "trans_name=#{transName},shop_id=#{shopId},charge_type=#{chargeType},is_free_fee=#{isFreeFee},has_free_condition=#{hasFreeCondition} " +
            "WHERE transport_id=#{transportId} ")
    int updateById(Transport transport);

    @Select("SELECT * FROM tz_transport WHERE shop_id=#{shopId}")
    List<Transport> list(Long shopId);

    @Select("SELECT * FROM tz_transport " +
            "WHERE transport_id = #{id}")
    Transport selectById(Long id);

    /**
     * 根据运费模板id获取运费项和运费城市.0
     *
     * @param id
     * @return
     */
    @Select("SELECT port.*,fee.*,city.*,ta.*  FROM tz_transport port " +
            "LEFT JOIN tz_transfee fee ON port.transport_id = fee.transport_id " +
            "LEFT JOIN tz_transcity city  ON  fee.transfee_id = city.transfee_id " +
            "LEFT JOIN  tz_area ta ON city.city_id = ta.area_id " +
            "WHERE port.transport_id = #{id}  order by fee.transfee_id")
    List<Transport> getTransportAndTransfeeAndTranscity(Long id);

    /**
     * 根据运费模板id删除运费模板
     *
     * @param ids
     */
    @Delete({
            "<script>",
            "DELETE  FROM tz_transport ",
            "WHERE transport_id IN ",
            "( ",
            "<foreach collection='ids' item='id' OPEN='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteTransports(@Param("ids") Long[] ids);

    /**
     * 运费模板id获取包邮运费项和城市
     *
     * @param transportId
     * @return
     */
    @Results({
            @Result(property = "transfeeFreeId", column = "transfee_free_id"),
            @Result(property = "freeCityList", javaType = List.class, column = "transfee_free_id", many = @Many(select = "listCity"))
    })
    @Select({
            "<script>",
            "SELECT * FROM tz_transfee_free ",
            "WHERE transport_id=#{transportId} ",
            "ORDER BY transfee_free_id DESC",
            "</script>"
    })
    List<TransfeeFree> getTransfeeFreeAndTranscityFreeByTransportId(@Param("transportId") Long transportId);

    @Select({"SELECT ta.* FROM tz_area ta ",
            "LEFT JOIN tz_transcity_free cityfree ON ta.area_id = cityfree.free_city_id ",
            "WHERE cityfree.transfee_free_id=#{transfeeFreeId} "
    })
    List<Area> listCity(@Param("transfeeFreeId") String transfeeFreeId);
}
