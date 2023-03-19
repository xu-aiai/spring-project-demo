package com.example.infrastructure.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * sheet2 页的头
 **/
public class DgJob {

    @ExcelProperty(index = 0)
    private String iJobId;

    @ExcelProperty(index = 1)
    private String cJobName;

    @ExcelProperty(index = 2)
    private String cEngineType;

    @ExcelProperty(index = 3)
    private String iSrcConnId;

    @ExcelProperty(index = 4)
    private String cSrcDataCfg;

    @ExcelProperty(index = 5)
    private String iDestConnId;

    @ExcelProperty(index = 6)
    private String cDestDataCfg;

    @ExcelProperty(index = 7)
    private String cMappingsCfg;

    @ExcelProperty(index = 8)
    private String cParamCfg;

    @ExcelProperty(index = 9)
    private String cBeforeEvents;

    @ExcelProperty(index = 10)
    private String cAfterEvents;

    @ExcelProperty(index = 11)
    private String cProjectId;

    @ExcelProperty(index = 12)
    private String cEnable;

    @ExcelProperty(index = 13)
    private String cDescription;

    @ExcelProperty(index = 14)
    private String subsysNo;

    public String getiJobId() {
        return iJobId;
    }

    public void setiJobId(String iJobId) {
        this.iJobId = iJobId;
    }

    public String getcJobName() {
        return cJobName;
    }

    public void setcJobName(String cJobName) {
        this.cJobName = cJobName;
    }

    public String getcEngineType() {
        return cEngineType;
    }

    public void setcEngineType(String cEngineType) {
        this.cEngineType = cEngineType;
    }

    public String getiSrcConnId() {
        return iSrcConnId;
    }

    public void setiSrcConnId(String iSrcConnId) {
        this.iSrcConnId = iSrcConnId;
    }

    public String getcSrcDataCfg() {
        return cSrcDataCfg;
    }

    public void setcSrcDataCfg(String cSrcDataCfg) {
        this.cSrcDataCfg = cSrcDataCfg;
    }

    public String getiDestConnId() {
        return iDestConnId;
    }

    public void setiDestConnId(String iDestConnId) {
        this.iDestConnId = iDestConnId;
    }

    public String getcDestDataCfg() {
        return cDestDataCfg;
    }

    public void setcDestDataCfg(String cDestDataCfg) {
        this.cDestDataCfg = cDestDataCfg;
    }

    public String getcMappingsCfg() {
        return cMappingsCfg;
    }

    public void setcMappingsCfg(String cMappingsCfg) {
        this.cMappingsCfg = cMappingsCfg;
    }

    public String getcParamCfg() {
        return cParamCfg;
    }

    public void setcParamCfg(String cParamCfg) {
        this.cParamCfg = cParamCfg;
    }

    public String getcBeforeEvents() {
        return cBeforeEvents;
    }

    public void setcBeforeEvents(String cBeforeEvents) {
        this.cBeforeEvents = cBeforeEvents;
    }

    public String getcAfterEvents() {
        return cAfterEvents;
    }

    public void setcAfterEvents(String cAfterEvents) {
        this.cAfterEvents = cAfterEvents;
    }

    public String getcProjectId() {
        return cProjectId;
    }

    public void setcProjectId(String cProjectId) {
        this.cProjectId = cProjectId;
    }

    public String getcEnable() {
        return cEnable;
    }

    public void setcEnable(String cEnable) {
        this.cEnable = cEnable;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public String getSubsysNo() {
        return subsysNo;
    }

    public void setSubsysNo(String subsysNo) {
        this.subsysNo = subsysNo;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("<items xsi:type=\"singleTable:MasterItem\"");
        if (getiJobId() != null) {
            result.append(" i_job_id=\"" + iJobId + '\"');
        }
        if (getcJobName() != null) {
            result.append(" c_job_name=\"" + cJobName + '\"');
        }
        if (getcEngineType() != null) {
            result.append(" c_engine_type=\"" + cEngineType + '\"');
        }
        if (getiSrcConnId() != null) {
            result.append(" i_src_conn_id=\"" + iSrcConnId + '\"');
        }
        if (getcSrcDataCfg() != null) {
            cSrcDataCfg = cSrcDataCfg.replace("\"", "&quot;");
            result.append(" c_src_data_cfg=\"" + cSrcDataCfg + '\"');
        }
        if (getiDestConnId() != null) {
            result.append(" i_dest_conn_id=\"" + iDestConnId + '\"');
        }
        if (getcDestDataCfg() != null) {
            cDestDataCfg = cDestDataCfg.replace("\"", "&quot;");
            result.append(" c_dest_data_cfg=\"" + cDestDataCfg + '\"');
        }
        if (getcMappingsCfg() != null) {
            cMappingsCfg = cMappingsCfg.replace("\"", "&quot;");
            result.append(" c_mappings_cfg=\"" + cMappingsCfg + '\"');
        }
        if (getcParamCfg() != null) {
            result.append(" c_param_cfg=\"" + cParamCfg + '\"');
        }
        if (getcBeforeEvents() != null) {
            result.append(" c_before_events=\"" + cBeforeEvents + '\"');
        }
        if (getcAfterEvents() != null) {
            result.append(" c_after_events=\"" + cAfterEvents + '\"');
        }
        if (getcProjectId() != null) {
            result.append(" c_project_id=\"" + cProjectId + '\"');
        }
        if (getcEnable() != null) {
            result.append(" c_enable=\"" + cEnable + '\"');
        }
        if (getcDescription() != null) {
            result.append(" c_description=\"" + cDescription + '\"');
        }
        if (getcEnable() != null) {
            result.append(" subsys_no=\"" + subsysNo + '\"');
        }
        result.append("/>");
        return result.toString();
    }
}
