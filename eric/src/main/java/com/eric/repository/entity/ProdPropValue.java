/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.eric.repository.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lanhai
 */
@Data
public class ProdPropValue implements Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = 6604406039354172708L;

	/**
     * 属性值ID
     */
    private Long valueId;

    /**
     * 属性值名称
     */

    private String propValue;

    /**
     * 属性ID
     */

    private Long propId;

}
