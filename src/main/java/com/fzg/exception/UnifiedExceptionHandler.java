package com.fzg.exception;

import com.fzg.result.ResponseEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = MMallException.class)
    public ModelAndView handlerException(MMallException e){
        ModelAndView modelAndView = new ModelAndView();
        ResponseEnum responseEnum = e.getResponseEnum();
        switch (responseEnum.getCode()) {
            case 301:
                modelAndView.setViewName("register");
                modelAndView.addObject("emailError",responseEnum.getMsg());
                break;
            case 302:
                modelAndView.setViewName("register");
                modelAndView.addObject("mobileError",responseEnum.getMsg());
                break;
            case 303:
                modelAndView.setViewName("register");
                modelAndView.addObject("userNameExists",responseEnum.getMsg());
                break;
            case 304:
                modelAndView.setViewName("login");
                modelAndView.addObject("userNameError",responseEnum.getMsg());
                break;
            case 305:
                modelAndView.setViewName("register");
                modelAndView.addObject("passwordError",responseEnum.getMsg());
                break;
        }
        return modelAndView;
    }
}
