package com.fzg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzg.entity.Product;
import com.fzg.mapper.ProductMapper;
import com.fzg.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> findAllByTypeAndProductCategoryId(Integer type, Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        String column = null;
        switch(type){
            case 1:
                column = "categorylevelone_id";
                break;
            case 2:
                column = "categoryleveltwo_id";
                break;
            case 3:
                column = "categorylevelthree_id";
                break;
        }
        queryWrapper.eq(column,id);
        List<Product> productList = this.productMapper.selectList(queryWrapper);
        return productList;
    }
}
