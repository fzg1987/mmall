package com.fzg.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzg.entity.Cart;
import com.fzg.entity.User;
import com.fzg.exception.MMallException;
import com.fzg.result.ResponseEnum;
import com.fzg.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
@Controller
@RequestMapping("/cart")
@Slf4j
public class CartController {
    @Autowired
    private CartService cartService;
    /**
     * 添加购物车
     * @param productId
     * @return
     */
    @GetMapping("/add/{productId}/{price}/{quantity}")
    public String add(
            @PathVariable("productId") Integer productId,
            @PathVariable("price") Float price,
            @PathVariable("quantity") Integer quantity,
            HttpSession session
    ){
        if(productId == null || price == null || quantity == null){
            log.info("【添加购物车】参数为空");
            throw new MMallException(ResponseEnum.PARAMETER_NULL);
        }
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("【添加购物车】当前为未登录状态");
            throw new MMallException(ResponseEnum.NOT_LOGIN);
        }
        Cart cart = new Cart();
        cart.setUserId(user.getId());
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cart.setCost(price * quantity);
        Boolean add = this.cartService.add(cart);
        if(!add){
            log.info("【添加购物车】添加失败");
            throw new MMallException(ResponseEnum.CART_ADD_ERROR);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        QueryWrapper<Cart> queryWrapper1 = new QueryWrapper<Cart>();
        queryWrapper1.eq("user_id",user.getId());
        modelAndView.addObject("cartList", this.cartService.list(queryWrapper1));
        return "redirect:/cart/get";
    }

    @GetMapping("/get")
    public ModelAndView get(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("【添加购物车】当前为未登录状态");
            throw new MMallException(ResponseEnum.NOT_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        modelAndView.addObject("cartList", this.cartService.findVOListByUserId(user.getId()));
        return modelAndView;
    }
}

