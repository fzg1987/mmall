package com.fzg.mapper;

import com.fzg.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    public int setDefault(Integer id);
}
