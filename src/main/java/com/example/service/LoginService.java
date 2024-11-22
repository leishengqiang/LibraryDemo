package com.example.service;

import com.example.Result.LoginResult;
import com.example.form.LoginForm;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
//@Transactional
public interface LoginService {
    /**
     * 登录校验
     * @param loginform
     * @return
     */
    public LoginResult login(LoginForm loginform);
}
