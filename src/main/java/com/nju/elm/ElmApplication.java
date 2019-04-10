package com.nju.elm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@MapperScan("com.nju.elm.Dao.Mappers")
@SpringBootApplication(scanBasePackages = "com.nju.elm")
@ServletComponentScan
public class ElmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElmApplication.class, args);
    }

}

