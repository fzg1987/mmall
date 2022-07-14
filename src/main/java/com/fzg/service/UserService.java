package com.fzg.service;

import com.fzg.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzg.form.UserRegisterForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface UserService extends IService<User> {
    public User register(UserRegisterForm userRegisterForm);

}
