package com.eric.repository;

import com.eric.repository.dto.DiscountChartReqVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAppDailyVisitsDao {

    /**
     * app访问量由Redis保存至数据库
     *
     * @param appDailyVisitsVo appDailyVisitsVo
     */
    void insertDailyVisit(AppDailyVisitsVo appDailyVisitsVo);

    /**
     * 按天查询访问量
     *
     * @return List<AppDailyVisitsVo>
     */
    @Select("SELECT  visits_date, max(visits_num) visits_num " +
            "FROM tpl_app_daily_visits_t " +
            "WHERE  date_format(visits_date, '%Y-%m-%d') > date_format(#{startDate}, '%Y-%m-%d') " +
            "AND  date_format(visits_date, '%Y-%m-%d') <  date_format(#{endDate}, '%Y-%m-%d') " +
            "GROUP BY visits_date " +
            "ORDER BY date_format(visits_date, '%Y-%m-%d')")
    List<AppDailyVisitsVo> dailyVisitByDay(DiscountChartReqVo vo);

    /**
     * 按月查询访问量
     *
     * @return List<AppDailyVisitsVo>
     */
    @Select("SELECT  date_format(visits_date, '%Y-%m') AS visits_date, sum(visits_num) AS visits_num " +
            "FROM tpl_app_daily_visits_t " +
            "WHERE  date_format(visits_date, '%Y-%m') > date_format(#{startDate}, '%Y-%m') " +
            "AND  date_format(visits_date, '%Y-%m')  < date_format(#{endDate}, '%Y-%m') " +
            "GROUP BY date_format(visits_date, '%Y-%m') " +
            "ORDER BY visits_date")
    List<AppDailyVisitsVo> dailyVisitByMouth(DiscountChartReqVo vo);


}
