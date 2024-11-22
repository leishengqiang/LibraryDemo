package com.example.controller;


import com.example.entity.User;
import com.example.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return "/user/"+url;
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
    @PostMapping("/add")
    public String add(User user){
        this.userService.save(user);
        return "redirect:/sysadmin/userList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        User user = this.userService.getById(id);
        model.addAttribute("user", user);
        return "/sysadmin/updateUser";
    }

    @PostMapping("/update")
    public String update(User user){
        this.userService.updateById(user);
        return "redirect:/sysadmin/userList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        this.userService.removeById(id);
        return "redirect:/sysadmin/userList";
    }
}
