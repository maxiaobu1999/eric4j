package com.chinasoft.repository;

import com.chinasoft.repository.entity.ChatEntity;
import com.chinasoft.repository.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 聊天
 */
@Repository
public interface IChatDao {
    //COMMENT = '单聊记录表';
    @Update({"CREATE TABLE IF NOT EXISTS ${tableName}( id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            " messageId BIGINT UNSIGNED NOT NULL," +
            " fromId BIGINT UNSIGNED NOT NULL," +
            " toId BIGINT UNSIGNED NOT NULL," +
            " content TEXT default NULL," +
            " msgType TEXT default NULL," +
            " stamp BIGINT default NULL," +
            " created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            " updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)" +
            " ENGINE=InnoDB"})
    void createSingleChat(@Param("tableName") String tableName);

    /**
     * 查询chat单表 fromId
     */
    @Select("SELECT * FROM ${tableName}")
    ArrayList<ChatEntity> queryChatByTableName(@Param("tableName") String tableName);

    // fromId表中 发给toId的信息
    @Select("SELECT * FROM ${tableName} where toId=#{toId} ")
    ArrayList<ChatEntity> queryChatByToId(@Param("tableName") String tableName, @Param("toId") Long toId);


    /**
     * 增加一条
     *
     * @param item 用户信息
     */
//    @Insert("INSERT INTO user(userId,phoneNum,userName,password," + "avatar," + "nickname)" +
    @Insert("INSERT INTO ${tableName}(messageId,fromId,toId,stamp,content,msgType)" +
            "values(#{item.messageId},#{item.fromId},#{item.toId},#{item.stamp},#{item.content},#{item.msgType})")
    int insertItem(@Param("tableName") String tableName, @Param("item") ChatEntity item);




    /**
     * 获取所有表信息
     **/
    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<Map> listTable();
    /**
     * 获取表字段信息
     **/
    @Select("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName}")
    List<Map> listTableColumn(String tableName);
    /**
     * 获取表中所有数据
     **/
    @Select("select * from ${tableName}")
    List<Map<String,String>> listTableValue(String tableName);
//
//    /**
//     * 根据userId获取查询用户信息
//     */
//    @Select("SELECT * FROM user where userId=#{userId} ")
//    List<UserEntity> queryByUserId(Long userId);
//    /**
//     * 根据username获取查询用户信息
//     */
//    @Select("SELECT * FROM user where userName=#{userName} ")
//    List<UserEntity> queryByUsername(String userName);
//
//
//    /**
//     * 根据userId更新
//     */
//    @Update("update user set phoneNum=#{phoneNum}," + "userName=#{userName}," + "password=#{password}," +
//            "avatar=#{avatar}," + "nickName=#{nickName}," + "token=#{token} where userId=#{userId}")
//    void updateByUserId(UserEntity user);
//
//
//    /**
//     * 根据 phoneNum 获取查询用户信息
//     */
//    @Select("SELECT * FROM user where phoneNum=#{phoneNum} ")
//    List<UserEntity> findByphoneNum(String phoneNum);

////    /**
////     * 获取all
////     */
////    @Select("SELECT * FROM haokan")
////    List<MiniEntity> getAll();
//
//
//    //    @Insert("insert into mini(" +
////            "HW," +
////            "mode," +
////            "miniLandPoster)" +
////            "values(" +
////            "#{HW}," +
////            "#{mode}," +
////            "#{miniLandPoster})")
//
//    //
////    /**
////     * 根据名称修改影片
////     */
////    @Update("update movie set name=#{name},title_tip=#{title_tip}" +
////            ",type=#{type},tag=#{tag},cover_url=#{cover_url},score=#{score},score_total=#{score_total}" +
////            ",score_num=#{score_num},alias=#{alias},director=#{director},actors=#{actors}," +
////            "language=#{language},region=#{region},release_time=#{release_time},length=#{length},update_time=#{update_time}," +
////            "views_total=#{views_total},views_today=#{views_today},summary=#{summary} where name=#{name}")
////    void updateMovie(Movie movie);
////
////    /**
////     * 查询所有视频
////     */
////    @Select("select * from movie")
////    List<Movie> findAll();
////
////    /**
////     * 根据影片名称查询影片
////     */
////    @Select("select * from movie  where name=#{name} ")
//////    @ResultMap("movieMap")
////    List<Movie> findByName(String name);
////
////
////    /**
////     * 根据类别查询影片
////     * 国产动漫
////     * 日韩动漫
////     * 欧美动漫
////     * 港台动漫
////     * 海外动漫
////     */
////    @Select("select * from movie where type=#{type}")
////    List<Movie> findByType(String type);
}
