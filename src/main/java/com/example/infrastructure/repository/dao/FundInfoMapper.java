package com.example.infrastructure.repository.dao;

import com.example.infrastructure.repository.po.FundInfoDO;
import java.util.List;

import com.example.domain.fund.FundInfoQueryRequest;
import com.example.domain.fund.FundInfoQueryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FundInfoMapper {

    public List<FundInfoDO> listFundInfo(@Param("fundId") Integer fundId);


    public List<FundInfoQueryResponse> listFundInfo2(FundInfoQueryRequest request);
}
