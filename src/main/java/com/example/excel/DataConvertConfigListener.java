package com.example.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.constant.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataConvertConfigListener extends AnalysisEventListener<DataConvertConfig> {

    private static final Logger logger = LoggerFactory.getLogger(DataConvertConfigListener.class);

    List<DataConvertConfig> dataConvertConfigList = new ArrayList<>(Constants.DEFAULT_COLLECTION_SIZE);

    /**
     * 每读取一行数据，都会调用该方法
     * @param data:
	 * @param context:
     * @return: void
     **/
    @Override
    public void invoke(DataConvertConfig data, AnalysisContext context) {
        dataConvertConfigList.add(data);
        /*if ("varchar".equals(data.getSchemetypeName()) || "char".equals(data.getSchemetypeName())) {
            data.setDefaultValue("");
        }
        if ("decimal".equals(data.getSchemetypeName())) {
            data.setDefaultValue("0.0");
        }
        System.out.println(data);*/
    }

    /**
     * 读取完整个 sheet 后会调用该方法
     * @param context:
     * @return: void
     **/
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("-------- sheet1 解析完成 ------------");
    }

    /**
     * 读取 sheet 页的头信息
     * @param context:
     * @return: void
     **/
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        /*logger.info("解析到一条头数据:{}", JSON.toJSONString(headMap));*/
    }



    public List<DataConvertConfig> listDataConvertConfig() {
        return dataConvertConfigList;
    }
}
