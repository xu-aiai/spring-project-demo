package com.example.entity;

import lombok.Data;

/**
 * 产品信息类
 **/
@Data
public class FundInfoDO {

    /**
     * 产品序号
     **/
    private Integer fundId;

    /**
     * 产品名称
     **/
    private String fundName;

    @Override
    public String toString() {
        return "FundInfoDO{" +
            "fundId=" + fundId +
            ", fundName='" + fundName + '\'' +
            '}';
    }
}
