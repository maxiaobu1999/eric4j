package com.eric.repository.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author lanhai
 */
@Data
public class ProdProp {

    /**
     * 属性id
     */
    private Long propId;

    /**
     * 属性名称
     */
    @NotBlank(message = "属性名称不能为空")
    private String propName;

    /**
     * 1:销售属性(规格); 2:参数属性;
     */
    private Integer rule;

    private Long shopId;

    /**
     * 属性值
     */
    @NotEmpty(message="规格属性值不能为空")
    private List<ProdPropValue> prodPropValues;

}
