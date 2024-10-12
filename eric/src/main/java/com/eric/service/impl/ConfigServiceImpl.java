package com.eric.service.impl;

import cn.hutool.core.date.DateUtil;
import com.eric.repository.*;
import com.eric.repository.dto.DiscountChartReqVo;
import com.eric.service.ConfigService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private IConfigDao mConfigDao;
    @Resource
    private IAppDailyVisitsDao mAppDailyVisitsDao;
    @Override
    public List<ConfigItemVo> accessTypeFilter() {
        return mConfigDao.accessTypeFilter();
    }

    @Override
    public HashMap<String, List<String>> discountChart(DiscountChartReqVo vo) {
        getStartEndDate(vo);
        List<AppDailyVisitsVo> dailyVisits;
        if ("day".equals(vo.getType())) {
            dailyVisits = mAppDailyVisitsDao.dailyVisitByDay(vo);
        } else {
            dailyVisits = mAppDailyVisitsDao.dailyVisitByMouth(vo);
        }
        HashMap<String, List<String>> hashMap = new HashMap<>();
        List<String> dateCollect = dailyVisits.parallelStream().map(AppDailyVisitsVo::getVisitsDate).collect(Collectors.toList());
        List<String> numCollect = dailyVisits.parallelStream().map(AppDailyVisitsVo::getVisitsNum).collect(Collectors.toList());
        hashMap.put("dayMonth", dateCollect);
        hashMap.put("data", numCollect);
        return hashMap;
    }
    private void getStartEndDate(DiscountChartReqVo vo) {
        vo.setEndDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        switch (vo.getDate()) {
            case "近7天":
                vo.setStartDate(startDate("day", 7));
                break;
            case "最近一个月":
                vo.setStartDate(startDate("month", 1));
                break;
            case "最近三个月":
                vo.setStartDate(startDate("month", 3));
                break;
            case "最近六个月":
                vo.setStartDate(startDate("month", 6));
                break;
            case "最近一个年":
                vo.setStartDate(startDate("month", 12));
                break;
        }
    }
    public String startDate(String type, Integer num) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if ("day".equals(type)) {
            instance.add(Calendar.DATE, -num);
        } else {
            instance.add(Calendar.MONTH, -num);
        }
        Date time = instance.getTime();
        return simpleDateFormat.format(time);
    }
}
