package com.eric.service;


import com.eric.repository.ConfigItemVo;
import com.eric.repository.dto.DiscountChartReqVo;

import java.util.HashMap;
import java.util.List;

public interface ConfigService {
    /**
     * 首页折现图下拉配置项
     *
     * @return List<ConfigItemVo>
     */
    List<ConfigItemVo> accessTypeFilter();

    HashMap<String, List<String>> discountChart(DiscountChartReqVo vo);

}
