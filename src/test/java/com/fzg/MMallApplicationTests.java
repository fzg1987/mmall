package com.fzg;

import com.fzg.service.ProductCategoryService;
import com.fzg.vo.ProductCategoryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MMallApplicationTests {
    @Autowired
    private ProductCategoryService service;

    @Test
    void contextLoads(){
        List<ProductCategoryVO> productCategoryVOList = this.service.buildProductCategoryMenu();
        int i = 0;
    }
}
