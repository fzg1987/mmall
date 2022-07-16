package com.fzg.mapper;

import com.fzg.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface ProductMapper extends BaseMapper<Product> {
    public Integer updateStockById(Integer id, Integer stock);
    public Integer getStockById(Integer id);
}
