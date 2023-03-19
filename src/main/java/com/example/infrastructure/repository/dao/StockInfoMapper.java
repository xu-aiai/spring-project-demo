package com.example.infrastructure.repository.dao;

import com.example.infrastructure.repository.po.StockInfoDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockInfoMapper {

    public List<StockInfoDO> listStockInfo(@Param("interCode") Integer interCode);
}
