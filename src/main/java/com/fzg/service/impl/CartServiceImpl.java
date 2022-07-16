package com.fzg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzg.entity.Cart;
import com.fzg.entity.Product;
import com.fzg.exception.MMallException;
import com.fzg.mapper.CartMapper;
import com.fzg.mapper.ProductMapper;
import com.fzg.result.ResponseEnum;
import com.fzg.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzg.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    @Transactional
    public Boolean add(Cart cart) {
        // 添加购物车
        int insert = this.cartMapper.insert(cart);
        if(insert != 1){
            log.info("【购物车添加】添加购物车失败");
            throw new MMallException(ResponseEnum.CART_ADD_ERROR);
        }
        // 商品减库存
        Integer stock = this.productMapper.getStockById(cart.getProductId());
        if(stock == null){
            log.info("【购物车添加】商品不存在");
            throw new MMallException(ResponseEnum.PRODUCT_NOT_EXISTS);
        }
        Integer newStock = stock - cart.getQuantity();
        if(newStock < 0){
            log.info("【购物车添加】商品库存不足");
            throw new MMallException(ResponseEnum.PRODUCT_STOCK_ERROR);
        }
        this.productMapper.updateStockById(cart.getProductId(), - cart.getQuantity());
        return true;
    }

    @Override
    public List<CartVO> findVOListByUserId(Integer userId) {

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
        queryWrapper.eq("user_id",userId);
        List<Cart> cartList = this.cartMapper.selectList(queryWrapper);
        List<CartVO> cartVOList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = this.productMapper.selectById(cart.getProductId());
            CartVO cartVO = new CartVO();
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO);
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }
}
