package com.example.service;


import com.example.domain.fund.FundInfoQueryRequest;
import com.example.domain.fund.FundInfoQueryResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface HelloService {

    public String sayHelloService(String name);


    public List<FundInfoQueryResponse> getFundInfoList(FundInfoQueryRequest fundInfoQueryRequest);

}
