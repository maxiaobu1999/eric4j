
package com.eric.repository.entity;


import java.io.Serializable;

/**
 * tz_transcity
 */
public class Transcity implements Serializable {


    private Long transcityId;

    /**
     * 运费项id
     */

    private Long transfeeId;

    /**
     * 城市id
     */

    private Long cityId;

    /**
     * 城市名称
     */
    private String areaName;

    public Long getTranscityId() {
        return transcityId;
    }

    public void setTranscityId(Long transcityId) {
        this.transcityId = transcityId;
    }

    public Long getTransfeeId() {
        return transfeeId;
    }

    public void setTransfeeId(Long transfeeId) {
        this.transfeeId = transfeeId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
