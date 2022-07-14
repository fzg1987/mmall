package com.fzg;

import com.fzg.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.fzg.mapper")
public class MmallStaticApplication {
    public static void main(String[] args) {
        SpringApplication.run(MmallStaticApplication.class,args);
    }
}
