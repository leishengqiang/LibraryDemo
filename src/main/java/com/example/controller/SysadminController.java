package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Book;
import com.example.entity.Sort;
import com.example.entity.User;
import com.example.service.IBookService;
import com.example.service.ISortService;
import com.example.service.IUserService;
import com.example.vo.BookVO;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
@Controller
@RequestMapping("/sysadmin")
public class SysadminController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBookService bookService;
    @Autowired
    private ISortService sortService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return "/sysadmin/"+url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/login";
    }

    @GetMapping("userList")
    public String userList(Model model){
        List<User> users = this.userService.list();
        model.addAttribute("list",users);
        return "sysadmin/user";
    }

    @PostMapping("findByName")
    public String findByName(String username,Model model){
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(username),"username",username);
        List<User> users = this.userService.list(userQueryWrapper);
        model.addAttribute("list",users);
        return "sysadmin/user";
    }

    @GetMapping("bookList")
    public String bookList(Model model){
        List<Book> books = this.bookService.list();
        List<BookVO> bookVOS=new ArrayList<>();
        for (Book book : books) {
            BookVO bookVO=new BookVO();
            BeanUtils.copyProperties(book,bookVO);
            Sort sort = this.sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOS.add(bookVO);
        }
        model.addAttribute("list",bookVOS);
        return "sysadmin/book";
    }

    @GetMapping("sortList")
    public String sortList(Model model){
        List<Sort> sorts = this.sortService.list();
        model.addAttribute("list",sorts);
        return "sysadmin/sort";
    }

}
