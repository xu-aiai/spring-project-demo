package com.example.domain.fund;

import lombok.Data;

/**
 * @author xu-ai-ai
 **/
@Data
public class FundInfoQueryResponse {
    private Integer fundId;
    private Integer fundCustId;
    private String fundName;
    private String fundStatus;
}
