package com.example.dao;

import com.example.entity.FundInfoDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FundInfoMapper {

    public List<FundInfoDO> listFundInfo(@Param("fundId") Integer fundId);
}
