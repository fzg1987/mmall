package com.fzg.service;

import com.fzg.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzg.entity.Orders;
import com.fzg.entity.User;
import com.fzg.vo.CartVO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface CartService extends IService<Cart> {
    public Boolean add(Cart cart);
    public List<CartVO> findVOListByUserId(Integer userId);
    public Boolean update(Integer id, Integer quantity, Float cost);
    public Boolean delete(Integer id);
    public Orders commit(String userAddress, String address, String remark, User user);
}
