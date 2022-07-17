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
        this.productMapper.updateStockById(cart.getProductId(), newStock);
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


    @Override
    @Transactional
    public Boolean update(Integer id, Integer quantity, Float cost) {
        //更新库存
        Cart cart = this.cartMapper.selectById(id);
        Integer oldQuantity = cart.getQuantity();
        if(quantity.equals(oldQuantity)){
            log.info("【更新购物车】参数错误");
            throw new MMallException(ResponseEnum.CART_UPDATE_PARAMETER_ERROR);
        }
        //查询商品库存
        Integer stock = this.productMapper.getStockById(cart.getProductId());
        Integer newStock = stock - (quantity - oldQuantity);
        if(newStock < 0){
            log.info("【更新购物车】商品库存错误");
            throw new MMallException(ResponseEnum.PRODUCT_STOCK_ERROR);
        }
        Integer integer = this.productMapper.updateStockById(cart.getProductId(), newStock);
        if(integer != 1){
            log.info("【更新购物车】更新商品库存失败");
            throw new MMallException(ResponseEnum.CART_UPDATE_STOCK_ERROR);
        }
        //更新数据
        int update = this.cartMapper.update(id, quantity, cost);
        if(update != 1){
            log.info("【更新购物车】更新失败");
            throw new MMallException(ResponseEnum.CART_UPDATE_ERROR);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Integer id) {
        //更新库存
        Cart cart = this.cartMapper.selectById(id);
        Integer stock = this.productMapper.getStockById(cart.getProductId());
        Integer newStock = stock + cart.getQuantity();
        Integer integer = this.productMapper.updateStockById(cart.getProductId(), newStock);
        if(integer != 1){
            log.info("【删除购物车】更新商品库存失败");
            throw new MMallException(ResponseEnum.CART_UPDATE_STOCK_ERROR);
        }
        // 删除购物车数据
        int i = this.cartMapper.deleteById(id);
        if(i != 1){
            log.info("【删除购物车】删除失败");
            throw new MMallException(ResponseEnum.CART_REMOVE_ERROR);
        }

        return true;
    }
}
