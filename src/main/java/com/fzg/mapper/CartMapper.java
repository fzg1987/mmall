package com.fzg.mapper;

import com.fzg.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface CartMapper extends BaseMapper<Cart> {
    public int update(Integer id, Integer quantity, Float cost);
    public Float getCostByUserId(Integer id);
}
