package com.example.dao;

import com.example.entity.FundInfoDO;
import com.example.entity.StockInfoDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockInfoMapper {

    public List<StockInfoDO> listStockInfo(@Param("interCode") Integer interCode);
}
