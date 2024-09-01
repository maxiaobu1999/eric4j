package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.entity.Sku;
import com.eric.repository.entity.TransfeeFree;
import com.eric.repository.entity.Transport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户信息
 */
@Repository
public interface ITransportDao {

    //    @Insert("INSERT INTO user(userId,phoneNum,userName,password," + "avatar," + "nickname)" +
    @Insert("INSERT INTO tz_transport(userId,phoneNum,userName,password,avatar,nickName,token)" +
            "values(#{userId},#{phoneNum},#{userName},#{password}" + ",#{avatar}" + ",#{nickName},#{token})")
    int insert(Transport item);

    int updateById(Transport transport);
    /**
     * 根据运费模板id获取运费项和运费城市.0
     *
     * @param id
     * @return
     */
    @Select("SELECT port.*,fee.*,city.*,ta.*  FROM tz_transport port " +
            "LEFT JOIN tz_transfee fee ON port.transport_id = fee.transport_id " +
            "LEFT JOIN tz_transcity city  ON  fee.transfee_id = city.transfee_id " +
            "LEFT JOIN  tz_area ta   ON  city.city_id = ta.area_id " +
            "WHERE port.transport_id = #{id}  order by fee.transfee_id")
    Transport getTransportAndTransfeeAndTranscity(Long id);

    /**
     * 根据运费模板id删除运费模板
     * @param ids
     */
    void deleteTransports(@Param("ids") Long[] ids);

    /**
     * 运费模板id获取包邮运费项和城市
     * @param transportId
     * @return
     */
    @Select("SELECT feefree.*,cityfree.*,ta.*  FROM  tz_transfee_free feefree " +
            "LEFT JOIN tz_transcity_free cityfree  ON feefree.transfee_free_id = cityfree.transfee_free_id " +
            "LEFT JOIN tz_area ta   ON  cityfree.free_city_id = ta.area_id " +
            "WHERE  feefree.transport_id = #{transportId}  order by feefree.transfee_free_id")
    List<TransfeeFree> getTransfeeFreeAndTranscityFreeByTransportId(@Param("transportId") Long transportId);

}
