package com.example.init.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源上下文持有类
 **/
@Slf4j
public class DataSourceContextHolder {

    /**
     * 动态数据源上下文
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();


    /**
     * 设置数据源
     * @param key
     */
    public static void setContextKey(String key) {
        log.info("切换数据源为:{}", key);
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    /**
     * 获取数据源名称
     * @return
     */
    public static String getContextKey(){
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? "secondDataSource" : key;
    }


    /**
     * 删除当前数据源名称
     */
    public static void removeContextKey(){
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}
