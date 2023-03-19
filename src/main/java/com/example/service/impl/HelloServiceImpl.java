package com.example.service.impl;

import com.example.infrastructure.repository.dao.FundInfoMapper;
import com.example.service.HelloService;
import com.example.domain.fund.FundInfoQueryRequest;
import com.example.domain.fund.FundInfoQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloServiceImpl implements HelloService {


    @Autowired
    private FundInfoMapper fundInfoMapper;

    @GetMapping("/hello")
    @Override
    public String sayHelloService(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @PostMapping("/getFundInfoList")
    @Override
    public List<FundInfoQueryResponse> getFundInfoList(@RequestBody FundInfoQueryRequest fundInfoQueryRequest) {
        List<FundInfoQueryResponse> fundInfoQueryResponses = fundInfoMapper.listFundInfo2(fundInfoQueryRequest);
        fundInfoQueryResponses.forEach(item -> item.setFundStatus("1"));
        return fundInfoQueryResponses;
    }

}
