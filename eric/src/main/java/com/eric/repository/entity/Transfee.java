package com.eric.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 运费项
 * tz_transfee
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
// 解决HttpMessageNotReadableException https://blog.csdn.net/fenfenguai/article/details/121752586
public class Transfee implements Serializable {
    private static final long serialVersionUID = 8039640964056626028L;

    /**
     * 运费项id
     */
    @Schema(description = "运费项id", required = true)
    private Long transfeeId;

    /**
     * 运费模板id
     */

    @Schema(description = "运费模板id", required = true)
    private Long transportId;

    /**
     * 续件数量
     */

    @Schema(description = "续件数量", required = true)
    private Double continuousPiece;

    /**
     * 首件数量
     */

    @Schema(description = "首件数量", required = true)
    private Double firstPiece;

    /**
     * 续件费用
     */

    @Schema(description = "续件费用", required = true)
    private Double continuousFee;

    /**
     * 首件费用
     */
    @Schema(description = "首件费用", required = true)
    private Double firstFee;

    @Schema(description = "指定条件运费城市项", required = true)
    private List<Area> cityList;

}
