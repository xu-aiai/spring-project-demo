package com.example.init.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/db.properties")
public class MultipleDataSourceConfig {



    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("firstDataSource", firstDataSource());
        dataSourceMap.put("secondDataSource", secondDataSource());
        dataSourceMap.put("thirdDataSource", thirdDataSource());
        dataSourceMap.put("fourthDataSource", fourthDataSource());
        //设置动态数据源
        DynamicDataSourceRouter dynamicDataSource = new DynamicDataSourceRouter();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(secondDataSource());
        return dynamicDataSource;
    }


    /**
     * 读取数据源的相关属性
     * @return: 数据源相关属性
     **/
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.first")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 根据数据源属性获取自定义数据源
     * @return 返回 Druid 数据源
     **/
    @Bean
    public DataSource firstDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder().type(DruidDataSource.class)
            .build();
    }

    /**
     * 读取数据源的相关属性
     * @return: 数据源相关属性
     **/
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.second")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * 根据数据源属性获取自定义数据源
     * @return 返回 Druid 数据源
     **/
    @Bean
    public DataSource secondDataSource() {
        return secondDataSourceProperties().initializeDataSourceBuilder().type(DruidDataSource.class)
            .build();
    }


    /**
     * 读取数据源的相关属性
     * @return: 数据源相关属性
     **/
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.third")
    public DataSourceProperties thirdDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 根据数据源属性获取自定义数据源
     * @return 返回 Druid 数据源
     **/
    @Bean
    public DataSource thirdDataSource() {
        return thirdDataSourceProperties().initializeDataSourceBuilder().type(DruidDataSource.class)
            .build();
    }


    /**
     * 读取数据源的相关属性
     * @return: 数据源相关属性
     **/
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.fourth")
    public DataSourceProperties fourthDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * 根据数据源属性获取自定义数据源
     * @return 返回 Druid 数据源
     **/
    @Bean
    public DataSource fourthDataSource() {
        return fourthDataSourceProperties().initializeDataSourceBuilder().type(DruidDataSource.class)
            .build();
    }

}
