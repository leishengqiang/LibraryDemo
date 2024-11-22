package com.example.controller;

import com.example.Result.LoginResult;
import com.example.entity.Admin;
import com.example.entity.Sysadmin;
import com.example.entity.User;
import com.example.form.LoginForm;
import com.example.service.IUserService;
import com.example.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//@RestController//返回JSON数据的API
@Controller//返回视图的控制器
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginservice;

    @PostMapping
    public String login(LoginForm loginform, Model model, HttpServletRequest request){
        LoginResult result= this.loginservice.login(loginform);
        String url = "";
        if(result.getCode().equals(-1) || result.getCode().equals(-2)){
            url="login";
            model.addAttribute("msg",result.getMsg());
        }else {
            HttpSession session=request.getSession();
            switch (loginform.getType()){
                case 0:
                    url="login";
                    model.addAttribute("msg",result.getMsg());
                    break;
                case 1:
                    session.setAttribute("user",(User)result.getObject());
                    url="redirect:/user/index";
                    break;
                case 2:
                    session.setAttribute("admin",(Admin)result.getObject());
                    url="redirect:/admin/index";
                    break;
                case 3:
                    session.setAttribute("sysadmin",(Sysadmin)result.getObject());
                    url="redirect:/sysadmin/index";
                    break;
            }
        }
        return url;
    }
}

