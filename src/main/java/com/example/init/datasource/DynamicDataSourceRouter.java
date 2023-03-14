package com.example.init.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 该类基于当前上下文动态确定实际数据源，即动态数据源路由
 * AbstractRoutingDatasource 需要知道要路由到哪个实际数据源的信息。这些信息通常被称为上下文。
 *
 **/

public class DynamicDataSourceRouter extends AbstractRoutingDataSource {

    /**
     * 确定此时使用哪个数据源，即路由策略
     **/
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getContextKey();
    }
}
