package com.example;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.init.datasource.DataSourceContextHolder;
import com.example.constant.Constants;
import com.example.dao.FundInfoMapper;
import com.example.dao.StockInfoMapper;
import com.example.excel.DataConvertConfig;
import com.example.excel.DataConvertConfigListener;
import com.example.excel.DgJob;
import com.example.excel.DgJobListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;

@Slf4j
//@Component
public class DataConvertConfigTest implements CommandLineRunner {

    @Autowired
    FundInfoMapper fundInfoMapper;

    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    DataConvertConfigListener dataConvertConfigListener;

    @Autowired
    DataSource dynamicDataSource;

    private Map<String, String> originalKeyColomnMap = new LinkedHashMap<>(Constants.DEFAULT_COLLECTION_SIZE);

    private Map<String, String > originalAllColomnMap = new LinkedHashMap<>(Constants.DEFAULT_COLLECTION_SIZE);

    private Map<String, String> targetKeyColomnMap = new HashMap<>(Constants.DEFAULT_COLLECTION_SIZE);

    public static Connection sourceConnection;
    public static Statement sourceStatement;

    public static Connection targetConnection;
    public static Statement targetStatement;


    /**
     * 自动化测试 Data Convert Config
     * @param args:
     * @return: void
     **/
    @Override
    public void run(String... args) throws Exception {
        List<DataConvertConfig> dataConvertConfigs = readExcel();
        Map<String, List<DataConvertConfig>> dataConvertConfigMap = dataConvertConfigs.stream()
            .collect(Collectors.groupingBy(DataConvertConfig::getTableName));
        // 切换 源库 数据源
        DataSourceContextHolder.setContextKey("thirdDataSource");
        // 创建 源库 连接
        sourceConnection = dynamicDataSource.getConnection();
        sourceConnection.setAutoCommit(false);
        // 创建 源库 statement
        sourceStatement = sourceConnection.createStatement();
        // 切换目标库数据源
        DataSourceContextHolder.setContextKey("fourthDataSource");
        // 创建 目标库 连接
        targetConnection = dynamicDataSource.getConnection();
        targetConnection.setAutoCommit(false);
        // 创建 源库 statement
        targetStatement = targetConnection.createStatement();
        //
        for(Map.Entry<String, List<DataConvertConfig>> entries : dataConvertConfigMap.entrySet()) {
            // 源表
            String originalTableName = entries.getKey();
            log.info("========================= {} 表开始测试同步 ===========================", originalTableName);
            ResultSet colomuMetaData = sourceConnection
                .getMetaData().getColumns(null, null, originalTableName.toUpperCase(), null);
            // 源表所有的字段和类型
            while (colomuMetaData.next()) {
                String colName = colomuMetaData.getString("COLUMN_NAME").toLowerCase();
                String dbType = colomuMetaData.getString("TYPE_NAME");

                originalAllColomnMap.put(colName, dbType);
            }
            // 源表的主键
            ResultSet primaryKeyResultSet = sourceConnection.getMetaData().getPrimaryKeys(null,null, originalTableName.toUpperCase());
            while(primaryKeyResultSet.next()){
                String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME").toLowerCase();
                originalKeyColomnMap.put(primaryKeyColumnName, null);
            }
            // 源表表涉及到的字段
            List<String> originalColomnList = entries.getValue().stream().map(DataConvertConfig::getFieldName)
                .collect(Collectors.toList());
            // 目标表的名称
            String targetTableName = entries.getValue().get(0).getTargetTableName();
            // 目标表涉及到的字段
            List<String> targetColomnList = entries.getValue().stream().map(DataConvertConfig::getFieldRemark)
                .collect(Collectors.toList());
            // 源表数据在目标表的标识唯一的主键。
            for (DataConvertConfig dataConvertConfig : entries.getValue()) {
                if (dataConvertConfig.getKeyfieldFlag().equals("1")) {
                    if (originalKeyColomnMap.containsKey(dataConvertConfig.getFieldRemark())) {
                        targetKeyColomnMap.put(dataConvertConfig.getFieldRemark(), null);
                    } else {
                        String value = "";
                        if ("varchar".equals(dataConvertConfig.getSchemetypeName()) || "char".equals(dataConvertConfig.getSchemetypeName())) {
                            value = "'" + dataConvertConfig.getDefaultValue() + "'";
                        } else if ("int".equals(dataConvertConfig.getSchemetypeName())) {
                            value =  dataConvertConfig.getDefaultValue();
                        }
                        targetKeyColomnMap.put(dataConvertConfig.getFieldRemark(), value);
                    }
                }
            }

            // 源表插入数据
            String insertSql = generateOriginalInsertSql(originalTableName, originalAllColomnMap, targetKeyColomnMap, originalKeyColomnMap);
            sourceStatement.execute(insertSql);
            sourceConnection.commit();
            // 源表读取数据 SQL
            String originalSelectSql = generateOriginalSelectSql(entries.getValue(), originalAllColomnMap, originalTableName, originalKeyColomnMap);
            // 目标表读数据 SQL
            String targetSelectSql = generateTargetSelectSql(entries.getValue(), targetTableName, targetKeyColomnMap);
            resultCompare(originalSelectSql,targetSelectSql,entries.getValue());
            // 源表更新
            String updateSql = generateOriginalUpdateSql(originalTableName, originalAllColomnMap, originalKeyColomnMap);
            sourceStatement.execute(updateSql);
            sourceConnection.commit();
            resultCompare(originalSelectSql,targetSelectSql,entries.getValue());
            // 源表删除数据
            String deleteSql = generateOriginalDeleteSql(originalTableName, originalKeyColomnMap);
            sourceStatement.execute(deleteSql);
            sourceConnection.commit();
            resultCompare(originalSelectSql,targetSelectSql,entries.getValue());
            log.info("========================= {} 表同步测试结束 ===========================", originalTableName);
            originalKeyColomnMap.clear();
            originalAllColomnMap.clear();
            targetKeyColomnMap.clear();

        }
        sourceStatement.close();
        sourceConnection.close();
        targetStatement.close();
        targetConnection.close();
        log.info("-----------------启动成功--------------------------");
    }

    public List<DataConvertConfig> readExcel() {
        // 读取部分sheet
        InputStream inputStream = null;
        try {
            ClassPathResource resource = new ClassPathResource("读取测试.xlsx");
            // 获取文件流
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream).build();
            // 读取 sheet1，生成
            ReadSheet readSheet1 =
                EasyExcel.readSheet(0).head(
                    DataConvertConfig.class).registerReadListener(dataConvertConfigListener).build();
            // 读取 sheet2
            ReadSheet readSheet2 =
                EasyExcel.readSheet(1).head(
                    DgJob.class).registerReadListener(new DgJobListener()).build();
            // 生成 datago, dataconvertconfig 配置
           excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        List<DataConvertConfig> dataConvertConfigs = dataConvertConfigListener.listDataConvertConfig();
        return dataConvertConfigs;
    }


    //其他数据库不需要这个方法 oracle和db2需要
    private static String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase().toString();
    }

    /**
     * 源表插入语句生成
     * @param originalTableName:
	 * @param originalAllColomnMap:
	 * @param targetKeyColomnMap:
     * @return: void
     **/
    public String generateOriginalInsertSql(String originalTableName, Map<String, String > originalAllColomnMap,
        Map<String, String> targetKeyColomnMap, Map<String, String> originalKeyColomnMap) {
        StringBuilder insertBeginSql = new StringBuilder("insert into " + originalTableName + "(");
        for (Map.Entry<String, String> entry : originalAllColomnMap.entrySet()) {
            insertBeginSql.append(entry.getKey() + ", ");
        }
        insertBeginSql.delete(insertBeginSql.length() - 2, insertBeginSql.length()).append(") values(");
        for (Map.Entry<String, String> entry : originalAllColomnMap.entrySet()) {
            String key = entry.getKey();
            String insertValue = "";
            if (entry.getValue().equals("NUMBER")) {
                insertValue = "999";
            } else if (entry.getValue().equals("VARCHAR2")) {
                insertValue = "\'xyz\'";
            } else if (entry.getValue().equals("CHAR")) {
                insertValue = "\'A\'";
            } else if (entry.getValue().equals("DATE")) {
                insertValue = "current_date";
            }
            insertBeginSql.append(insertValue +", ");
            if (targetKeyColomnMap.containsKey(key)) {
                targetKeyColomnMap.put(key, insertValue);
            }
            if (originalKeyColomnMap.containsKey(key)) {
                originalKeyColomnMap.put(key, insertValue);
            }

        }
        String insertSql = insertBeginSql.delete(insertBeginSql.length() - 2, insertBeginSql.length()).append(")").toString();
        log.info("源库插入 SQL : {}", insertSql);
        return insertSql;
    }

    /**
     * 生成 源表 更新 SQL
     * TODO：目标只支持更新非主键字段
     * @param originalTableName: 源表名
	 * @param originalAllColomnMap: 源表所有的字段
	 * @param originalKeyColomnMap: 源表的主键字段
     * @return: 生成的 源表 更新 语句
     **/
    public String generateOriginalUpdateSql(String originalTableName, Map<String, String > originalAllColomnMap, Map<String, String> originalKeyColomnMap) {
        StringBuilder updateBeginSql = new StringBuilder("update " + originalTableName + " set ");
        StringBuilder whereConditionSql = new StringBuilder(" where ");
        for (Map.Entry<String, String> entry : originalAllColomnMap.entrySet()) {
            String key = entry.getKey();
            String updateValue = "";
            if (!originalKeyColomnMap.containsKey(key)) {
                if (entry.getValue().equals("NUMBER")) {
                    updateValue = "888";
                } else if (entry.getValue().equals("VARCHAR2")) {
                    updateValue = "\'lmn\'";
                } else if (entry.getValue().equals("CHAR")) {
                    updateValue = "\'B\'";
                } else if (entry.getValue().equals("DATE")) {
                    updateValue =  "current_date + 1";
                }
                updateBeginSql.append(key + "=").append(updateValue).append(", ");
            } else {
                whereConditionSql.append(key + "=" + originalKeyColomnMap.get(key) + " and ");
            }

//            if (targetKeyColomnMap.containsKey(key)) {
//                targetKeyColomnMap.put(key, updateValue);
//            }
//            if (originalKeyColomnMap.containsKey(key)) {
//                originalAllColomnMap.put(key, updateValue);
//            }
            /*log.info("源表：{}，字段：{}，值：{}", originalTableName, key, insertValue);*/
        }
        updateBeginSql.deleteCharAt(updateBeginSql.length() - 2);
        String updateSql = updateBeginSql.append(
            whereConditionSql.delete(whereConditionSql.length() - 5, whereConditionSql.length()).toString()).toString();
        log.info("源表更新 SQL : {}", updateSql);
        return updateSql;
    }

    /**
     * 生成 源表 删除 SQL
     * @param originalTableName: 源表名
	 * @param originalKeyColomnMap: 源表主键集合
     * @return: java.lang.String 删除 SQL
     **/
    private String generateOriginalDeleteSql(String originalTableName, Map<String, String> originalKeyColomnMap) {
        StringBuilder deleteBeginSql = new StringBuilder("delete from " + originalTableName + " where ");
        for (Map.Entry<String, String> entry : originalKeyColomnMap.entrySet()) {
            deleteBeginSql.append(entry.getKey() + "=" + entry.getValue() + " and ");
        }
        String originalDeleteSql = deleteBeginSql.delete(deleteBeginSql.length() - 5, deleteBeginSql.length()).toString();
        log.info("源表删除 SQL : {}", originalDeleteSql);
        return originalDeleteSql;
    }

    /**
     * 生成源表的查询 Sql
     * @param dataConvertConfigList: dataConvertConfig  配置\
     * @param originalAllColomnMap 源表所有的字段
	 * @param originalTableName: 源表的名称
	 * @param originalKeyColomnMap: 源表的主键
     * @return: java.lang.String 生成的查询 Sql
     **/
    public String generateOriginalSelectSql(List<DataConvertConfig> dataConvertConfigList,
        Map<String, String > originalAllColomnMap, String originalTableName,
        Map<String, String> originalKeyColomnMap) {
        StringBuilder originalBeginSelectSql = new StringBuilder("select ");
        for (DataConvertConfig colomn : dataConvertConfigList) {
            if (originalAllColomnMap.containsKey(colomn.getFieldName())) {
                originalBeginSelectSql.append(colomn.getFieldName());
            } else {
                if ("varchar".equals(colomn.getSchemetypeName()) || "char".equals(colomn.getSchemetypeName())) {
                    originalBeginSelectSql.append("'" + colomn.getDefaultValue() + "'");
                } else if ("int".equals(colomn.getSchemetypeName()) || "decimal".equals(colomn.getSchemetypeName())) {
                    originalBeginSelectSql.append("'" + colomn.getDefaultValue() + "'");
                }
            }
            originalBeginSelectSql.append(" as " + colomn.getFieldRemark() + ", ");
        }
        originalBeginSelectSql.deleteCharAt(originalBeginSelectSql.length() - 2);
        originalBeginSelectSql.append(" from " + originalTableName + " where ");
        for (Map.Entry<String, String> entry : originalKeyColomnMap.entrySet()) {
            originalBeginSelectSql.append(entry.getKey() + "=" + entry.getValue() + " and ");
        }
        String originalSelectSql = originalBeginSelectSql.delete(originalBeginSelectSql.length() - 5, originalBeginSelectSql.length()).toString();
        log.info("源库查询 SQL : {}", originalSelectSql);
        return originalSelectSql;
    }


    /**
     * 生成目标表的查询 Sql
     * @param dataConvertConfigList: dataConvertConfig  配置
     * @param targetTableName 目标表的名称
     * @param targetKeyColomnMap: 目标表标识源表数据的主键
     * @return: java.lang.String 生成的查询 Sql
     **/
    public String generateTargetSelectSql(List<DataConvertConfig> dataConvertConfigList, String targetTableName,
        Map<String, String> targetKeyColomnMap) {

        StringBuilder targetSelectBeginSql = new StringBuilder("select ");
        for (DataConvertConfig colomn : dataConvertConfigList) {
            targetSelectBeginSql.append(colomn.getFieldRemark() + ", ");
        }
        targetSelectBeginSql.deleteCharAt(targetSelectBeginSql.length() - 2);
        targetSelectBeginSql.append(" from " + targetTableName + " where ");
        for (Map.Entry<String, String> entry : targetKeyColomnMap.entrySet()) {
            targetSelectBeginSql.append(entry.getKey() + "=" + entry.getValue() + " and ");
        }
        String targetSelectSql = targetSelectBeginSql.delete(targetSelectBeginSql.length() - 5, targetSelectBeginSql.length()).toString();
        log.info("目标库查询 SQL : {}", targetSelectSql);
        return targetSelectSql;
    }



    /**
     * 根据查询语句获取与源表数据和目标表数据
     * @param sourceSelectSql: 源表   查询 SQL
	 * @param targetSelectSql: 目标表 查询 SQL
	 * @param dataConvertConfigList: 同步的相关配置
     * @return: void
     **/
    public void resultCompare(String sourceSelectSql, String targetSelectSql,
        List<DataConvertConfig> dataConvertConfigList ) throws SQLException {
        ResultSet sourceSelectResultSet = sourceStatement.executeQuery(sourceSelectSql);
//        ResultSet targetSelectResultSet = targetStatement.executeQuery(targetSelectSql);
        StringBuilder sourceSelectResult = new StringBuilder();
        StringBuilder targetSelectResult = new StringBuilder();
        while (sourceSelectResultSet.next()) {
            for (DataConvertConfig colomn : dataConvertConfigList) {
                String colomnName = colomn.getFieldRemark();
                String value = sourceSelectResultSet.getString(colomnName.toUpperCase());
                sourceSelectResult.append(colomnName + "=" + value + ",");
            }
            sourceSelectResult.deleteCharAt(sourceSelectResult.length() - 1);
            log.info("原始值:{}", sourceSelectResult.toString());
        }
//        while (targetSelectResultSet.next()) {
//            for (DataConvertConfig colomn : dataConvertConfigList) {
//                String colomnName = colomn.getFieldRemark();
//                targetSelectResult.append(colomn + "=" + targetSelectResultSet.getString(colomnName.toUpperCase()) + ",");
//            }
//            targetSelectResult.deleteCharAt(targetSelectResult.length() - 1);
//            log.info("目标值:{}", targetSelectResult.toString());
//        }
    }

}
