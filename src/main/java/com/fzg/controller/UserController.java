package com.fzg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzg.entity.User;
import com.fzg.exception.MMallException;
import com.fzg.form.UserRegisterForm;
import com.fzg.result.ResponseEnum;
import com.fzg.service.UserService;
import com.fzg.utils.RegexValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
}

