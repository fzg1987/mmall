package com.fzg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzg.entity.User;
import com.fzg.entity.UserAddress;
import com.fzg.exception.MMallException;
import com.fzg.form.UserLoginForm;
import com.fzg.form.UserRegisterForm;
import com.fzg.result.ResponseEnum;
import com.fzg.service.CartService;
import com.fzg.service.OrdersService;
import com.fzg.service.UserAddressService;
import com.fzg.service.UserService;
import com.fzg.utils.RegexValidateUtil;
import com.fzg.vo.CartVO;
import com.fzg.vo.OrdersVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserAddressService userAddressService;

    /**
     * 用户注册
     * @param userRegisterForm
     * @param bindingResult
     * @return
     */

    @PostMapping("/register")
    public String register(@Valid UserRegisterForm userRegisterForm, BindingResult bindingResult){
        // 非空校验
        if(bindingResult.hasErrors()){
            log.info("【用户注册】用户信息不能为空");
            throw new MMallException(ResponseEnum.USER_INFO_NULL);
        }
        User register = this.userService.register(userRegisterForm);
        if(register == null){
            log.info("【用户注册】添加用户失败");
            throw new MMallException(ResponseEnum.USER_REGISTER_ERROR);
        }
        return "redirect:/login";
    }

    /**
     * 用户登录
     * @param userLoginForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid UserLoginForm userLoginForm, BindingResult bindingResult, HttpSession session){
        // 非空校验
        if(bindingResult.hasErrors()){
            log.info("【用户登录】用户信息不能为空");
            throw new MMallException(ResponseEnum.USER_INFO_NULL);
        }
        User login = this.userService.login(userLoginForm);
        session.setAttribute("user", login);

        return "redirect:/productCategory/main";
    }

    /**
     * 返回当前用户的订单列表
     * @return
     */
    @GetMapping("/orderList")
    public ModelAndView orderList(HttpSession session){
        // 判断是否为登录用户
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("【查询订单】当前为未登录状态");
            throw new MMallException(ResponseEnum.NOT_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        modelAndView.addObject("orderList",this.ordersService.findAllByUserId(user.getId()));
        // 登录用户，查询该用户的购物车记录
        modelAndView.addObject("cartList", this.cartService.findVOListByUserId(user.getId()));
        return modelAndView;
    }

    @GetMapping("/addressList")
    public ModelAndView addressList(HttpSession session){
        // 判断是否为登录用户
        User user = (User) session.getAttribute("user");
        if(user == null){
            log.info("【查询订单】当前为未登录状态");
            throw new MMallException(ResponseEnum.NOT_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        modelAndView.addObject("addressList",this.userAddressService.list(queryWrapper));
        // 登录用户，查询该用户的购物车记录
        modelAndView.addObject("cartList", this.cartService.findVOListByUserId(user.getId()));
        return modelAndView;
    }
}

