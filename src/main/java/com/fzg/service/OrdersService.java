package com.fzg.service;

import com.fzg.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzg.vo.OrdersVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface OrdersService extends IService<Orders> {
    public List<OrdersVO> findAllByUserId(Integer id);
}
