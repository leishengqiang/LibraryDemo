package com.example.controller;


import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.service.IBookService;
import com.example.service.IBorrowService;
import com.example.vo.AdminBorrowVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IBorrowService borrowService;
    @Autowired
    private IBookService bookService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return "/admin/" + url;
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping("/borrowList")
    public String borrowList(Model model){
        List<AdminBorrowVO> adminBorrowVOS = this.borrowService.adminborrowList();
        model.addAttribute("list",adminBorrowVOS);
        return "admin/borrow";
    }

    @GetMapping("allow")
    public String allow(Integer id){
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(1);
        this.borrowService.updateById(borrow);
        return "redirect:/admin/borrowList";
    }

    @GetMapping("notAllow")
    public String notAllow(Integer id){
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(2);
        Book book = this.bookService.getById(borrow.getBid());
        book.setNumber(book.getNumber()+1);
        this.bookService.updateById(book);
        this.borrowService.updateById(borrow);
        return "redirect:/admin/borrowList";
    }


}
