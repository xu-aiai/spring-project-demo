package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 项目入口-启动类。
 * 排除自动配置数据源，因为本项目手动配置了多数据源
 * @author laerla
 * @date 2021-07-01
 **/
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
