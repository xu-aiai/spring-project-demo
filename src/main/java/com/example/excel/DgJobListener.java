package com.example.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DgJobListener extends AnalysisEventListener<DgJob> {

    private static final Logger logger = LoggerFactory.getLogger(DgJobListener.class);

    @Override
    public void invoke(DgJob data, AnalysisContext context) {
        /*System.out.println(data);*/
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("-------- sheet2 解析完成 ------------");
    }
}
