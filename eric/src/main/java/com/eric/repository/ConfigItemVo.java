package com.eric.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigItemVo  {
    // 序号
    private Long serialNum;

    private Long configItemId;

    private Long configId;

    private String configItemCode;

    private String configItemName;

    private String configItemDesc;

    private String configCode;

    private String configName;

    private String configItemText;

    private String configItemType;

    private Integer configItemNumber;
}
