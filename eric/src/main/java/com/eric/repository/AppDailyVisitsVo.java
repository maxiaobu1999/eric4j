package com.eric.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDailyVisitsVo {
    private String visitsId;

    private String visitsDate;

    private String visitsNum;
}
