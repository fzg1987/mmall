package com.fzg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fzg.mapper")
public class MmallStaticApplication {
    public static void main(String[] args) {
        SpringApplication.run(MmallStaticApplication.class,args);
    }
}
