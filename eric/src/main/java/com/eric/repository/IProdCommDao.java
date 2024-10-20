package com.eric.repository;

import com.eric.repository.dto.ProdCommDto;
import com.eric.repository.entity.ProdComm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import com.eric.repository.dto.ProdCommDataDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论
 */
@Repository
public interface IProdCommDao {
    /**
     * 根据商品id获取商品评论信息
     *
     * @param prodId
     * @return
     */
    @Select("SELECT count(1) AS number, " +
            "count(CASE WHEN evaluate = 0 THEN 1 ELSE null END)     AS praise_number, " +
            "count(CASE WHEN evaluate = 1 THEN 1 ELSE null END)     AS secondary_number, " +
            "count(CASE WHEN evaluate = 2 THEN 1 ELSE null END)     AS negative_number, " +
            "count(CASE WHEN pics is not null THEN 1 ELSE null END) AS pic_number " +
            "FROM tz_prod_comm " +
            "WHERE prod_id = #{prodId}  AND status = 1")
    ProdCommDataDto getProdCommDataByProdId(@Param("prodId") Long prodId);

    /**
     * 根据评价等级和商品id分页获取商品评价
     *
     * @param prodId
     * @param evaluate
     * @return
     */
    @Select({
            "<script>",
            "SELECT pc.prod_comm_id, pc.reply_content,pc.rec_time, pc.score, pc.is_anonymous, pc.pics, pc.user_id, pc.reply_sts,  pc.evaluate, pc.content, u.nick_name, u.pic ",
            "FROM tz_prod_comm pc ",
            "LEFT JOIN tz_user u ON u.user_id=pc.user_id ",
            "WHERE pc.prod_id = #{prodId} AND pc.status = 1 ",

            "<if test='evaluate!=-1 and evaluate!=3'> ",
            "AND pc.evaluate = #{evaluate} ",
            "</if> ",

            "<if test='evaluate == 3'> ",
            "AND pc.pics IS NOT NULL ",
            "</if> ",

            "ORDER BY pc.rec_time DESC",
            "</script>"
    })
    List<ProdCommDto> getProdCommDtoPageByProdId(@Param("prodId") Long prodId, @Param("evaluate") Integer evaluate);

    /**
     * 根据用户id分页获取评论列表
     *
     * @param userId
     * @return
     */
    @Select("SELECT pc.prod_comm_id, pc.reply_content,pc.rec_time, pc.score, pc.is_anonymous, pc.pics, pc.user_id, pc.reply_sts,  pc.evaluate, pc.content, u.nick_name, u.pic " +
            "FROM  tz_prod_comm pc " +
            "LEFT JOIN tz_user u ON u.user_id=pc.user_id "+
            "WHERE pc.user_id = #{userId}  AND pc.status = 1")
    List<ProdCommDto> getProdCommDtoPageByUserId(@Param("userId") String userId);

    /**
     * 根据参数分页获取商品评论
     *
     * @param prodComm
     * @return
     */
    @Select({
            "<script>",
            "SELECT pc.prod_comm_id, pc.score, pc.is_anonymous, pc.status,  pc.rec_time, pc.reply_time, pc.evaluate, u.nick_name, u.user_mobile,  p.prod_name",
            "FROM tz_prod_comm pc ",
            "LEFT JOIN tz_prod p ON p.prod_id=pc.prod_id ",
            "LEFT JOIN tz_user u ON pc.user_id=u.user_id ",
            "<where>",
            "<if test='prodComm.status!=null'> ",
            "AND pc.status=#{prodComm.status} ",
            "</if> ",

            "<if test='prodComm.prodName!=null and prodComm.prodName!=null'> ",
            "AND p.prod_name like concat('%',#{prodComm.prodName},'%') ",
            "</if> ",
            "</where>",
            "</script>"
    })
    List<ProdComm> getProdCommPage(@Param("prodComm") ProdComm prodComm);


    /**
     * 添加评论
     * @param prodComm
     * @return
     */
    @Insert("INSERT INTO tz_prod_comm(prod_id,order_item_id,user_id,content,reply_content,rec_time,reply_time,reply_sts,postip,score,useful_counts,pics,is_anonymous,status,evaluate)" +
            "values(#{prodId},#{orderItemId},#{userId},#{content},#{replyContent},NOW(),#{replyTime},#{replySts},#{postip},#{score},#{usefulCounts},#{pics},#{isAnonymous},#{status},#{evaluate})")
    Integer save(ProdComm prodComm );

    /**
     * 删除评论
     * @param prodCommId
     * @return
     */
    @Delete("delete from tz_prod_comm where prod_comm_id=#{prodCommId} ")
    Integer removeById(Long prodCommId );
}
