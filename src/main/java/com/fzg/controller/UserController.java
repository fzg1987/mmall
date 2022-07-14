package com.fzg.controller;

import com.fzg.entity.User;
import com.fzg.exception.MMallException;
import com.fzg.form.UserRegisterForm;
import com.fzg.result.ResponseEnum;
import com.fzg.utils.RegexValidateUtil;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/register")
    public String register(@Valid UserRegisterForm userRegisterForm, BindingResult bindingResult){
        // 非空校验
        if(bindingResult.hasErrors()){
            log.info("【用户注册】用户信息不能为空");
            throw new MMallException(ResponseEnum.USER_INFO_NULL);
        }
        // 邮箱格式校验
        if(!RegexValidateUtil.checkEmail(userRegisterForm.getEmail())){
            log.info("【用户注册】邮箱格式错误");
            throw new MMallException(ResponseEnum.EMAIL_ERROR);
        }
        // 手机格式校验
        if(!RegexValidateUtil.checkMobile(userRegisterForm.getMobile())){
            log.info("【用户注册】手机格式错误");
            throw new MMallException(ResponseEnum.MOBILE_ERROR);
        }

        return null;
    }
}

