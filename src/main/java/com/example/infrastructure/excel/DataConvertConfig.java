package com.example.infrastructure.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * sheet1 页的头
 **/
public class DataConvertConfig {

    @ExcelProperty(index = 0)
    private String convertType;

    @ExcelProperty(index = 1)
    private String subType;

    @ExcelProperty(index = 2)
    private String origin;

    @ExcelProperty(index = 3)
    private String tableName;

    @ExcelProperty(index = 4)
    private String dbName;

    @ExcelProperty(index = 5)
    private String targetTableName;

    @ExcelProperty(index = 6)
    private String fieldName;

    @ExcelProperty(index = 7)
    private String fieldRemark;

    @ExcelProperty(index = 8)
    private String defaultValue;

    @ExcelProperty(index = 9)
    private String keyfieldFlag;

    @ExcelProperty(index = 10)
    private String schemetypeName;

    @ExcelProperty(index = 11)
    private String schemeTableAliasName;


    public String getConvertType() {
        return convertType;
    }

    public void setConvertType(String convertType) {
        this.convertType = convertType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldRemark() {
        return fieldRemark;
    }

    public void setFieldRemark(String fieldRemark) {
        this.fieldRemark = fieldRemark;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getKeyfieldFlag() {
        return keyfieldFlag;
    }

    public void setKeyfieldFlag(String keyfieldFlag) {
        this.keyfieldFlag = keyfieldFlag;
    }

    public String getSchemetypeName() {
        return schemetypeName;
    }

    public void setSchemetypeName(String schemetypeName) {
        this.schemetypeName = schemetypeName;
    }

    public String getSchemeTableAliasName() {
        return schemeTableAliasName;
    }

    public void setSchemeTableAliasName(String schemeTableAliasName) {
        this.schemeTableAliasName = schemeTableAliasName;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("<items xsi:type=\"singleTable:MasterItem\"");
        if (getConvertType() != null) {
            result.append(" convert_type=\"" + convertType + '\"');
        }
        if (getSubType() != null) {
            result.append(" sub_type=\"" + subType + '\"');
        }
        if (getOrigin() != null) {
            result.append(" origin=\"" + origin + '\"' );
        }
        if (getTableName() != null) {
            result.append(" table_name=\"" + tableName + '\"');
        }
        if (getDbName() != null) {
            result.append(" db_name=\"" + dbName + '\"');
        }
        if (getTargetTableName() != null) {
            result.append(" target_table_name=\"" + targetTableName + '\"');
        }
        if (getFieldName() != null) {
            result.append(" field_name=\"" + fieldName + '\"');
        }
        if (getFieldRemark() != null) {
            result.append(" field_remark=\"" + fieldRemark + '\"');
        }
        if (getDefaultValue() != null) {
            result.append(" default_value=\"" + defaultValue + '\"');
        }
        if (getKeyfieldFlag() != null) {
            result.append(" keyfield_flag=\"" + keyfieldFlag + '\"');
        }
        if (getSchemetypeName() != null) {
            result.append(" schemetype_name=\"" + schemetypeName + '\"');
        }
        if (getSchemeTableAliasName() != null) {
            result.append(" scheme_table_alias_name=\"" + schemeTableAliasName + '\"');
        }
        result.append("/>");
        return result.toString();

    }
}














