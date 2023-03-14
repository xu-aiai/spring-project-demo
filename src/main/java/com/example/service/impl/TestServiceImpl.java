package com.example.service.impl;

import com.example.threadpool.CustomMultiThreadingService;
import com.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServiceImpl implements TestService {

    @Autowired
    private CustomMultiThreadingService customMultiThreadingService;

    @GetMapping(value="/dotask")
    @Override
    public String testSpringMultipleThread() {

        for (int i=0;i<10;i++){
            customMultiThreadingService.executeAysncTask1(i);
            customMultiThreadingService.executeAsyncTask2(i);
        }

        return "success";
    }
}
