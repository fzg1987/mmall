package com.fzg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzg.entity.OrderDetail;
import com.fzg.entity.Orders;
import com.fzg.entity.Product;
import com.fzg.mapper.OrderDetailMapper;
import com.fzg.mapper.OrdersMapper;
import com.fzg.mapper.ProductMapper;
import com.fzg.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzg.vo.OrderDetailVO;
import com.fzg.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<OrdersVO> findAllByUserId(Integer id) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<Orders> ordersList = this.ordersMapper.selectList(queryWrapper);
        List<OrdersVO> ordersVOList = new ArrayList<>();
        for (Orders orders : ordersList) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(orders, ordersVO);
            QueryWrapper<OrderDetail> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("order_id",orders.getId());
            List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(queryWrapper1);
            List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                BeanUtils.copyProperties(orderDetail,orderDetailVO);
                Product product = this.productMapper.selectById(orderDetail.getProductId());
                BeanUtils.copyProperties(product, orderDetailVO);
                orderDetailVOList.add(orderDetailVO);
            }
            ordersVO.setOrderDetailList(orderDetailVOList);
            ordersVOList.add(ordersVO);
        }
        return ordersVOList;
    }
}
