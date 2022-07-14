package com.fzg.controller;

import com.fzg.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;


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
public class UserController {
    @PostMapping("/register")
    public String register(User user){
        System.out.println(user);
        return null;
//    public String register(@Valid UserRegisterForm userRegisterForm, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            log.info("【用户注册】用户信息不能为空");
////            throw new MMallException(ResponseEnum.USER_INFO_NULL);
////            List<ObjectError> allErrors = bindingResult.getAllErrors();
////            for (ObjectError allError : allErrors) {
////                System.out.println(allError);
////            }
//        }
//        return null;
    }
}

