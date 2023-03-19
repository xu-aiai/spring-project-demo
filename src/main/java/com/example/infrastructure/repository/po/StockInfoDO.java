package com.example.infrastructure.repository.po;

import lombok.Data;

@Data
public class StockInfoDO {

    /**
     * 证券内码
     **/
    private Integer interCode;

    /**
     * 证券名称
     **/
    private String stockName;
}
